package com.ocean.ishareeconomy_android.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ocean.ishareeconomy_android.database.entities.asDomainModel
import com.ocean.ishareeconomy_android.database.iShareDataBase
import com.ocean.ishareeconomy_android.database.relationships.asDomainModel
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.models.User
import com.ocean.ishareeconomy_android.network.Network
import com.ocean.ishareeconomy_android.network.asDatabaseModel
import com.ocean.ishareeconomy_android.network.asDatabaseModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository(private val loggedInUserId: String, private val database: iShareDataBase) {

    private val succes = MutableLiveData<String>()
    private val error = MutableLiveData<String>()

    private lateinit var loggedInUser: User

    val users: LiveData<List<User>> = Transformations.map(database.users.getAllUsers()) {
        it.asDomainModel().map {
            user ->
            user.lending = database.objects.lendingObjectsForUserById(user.id).asDomainModel()
            user
        }
    }

    val lending: LiveData<List<LendingObject>> =
        Transformations.map(database.objects.getLendingObjectsFromUserById(loggedInUserId)) {
        it.asDomainModel()
    }

    val using: LiveData<List<LendingObject>> =
        Transformations.map(database.objects.getObjectsCurrentlyUsedByUser(loggedInUserId)) {
        it.asDomainModel()
    }

    suspend fun refreshUsers(id: String, auth: String) {
        withContext(Dispatchers.IO) {
            val response = Network.users.getUsersAsync(id, auth).await()
            if (response.isSuccessful) {
                // Get the users
                var users = response.body()!!

                // Get the logged in user
                loggedInUser = users.find { user -> user.id == id }!!

                // Get all LendObjects
                val lendObjects = users.flatMap { user -> user.lending }!!

                // Get all Waiting ObjectUsers
                val objectUsersWaiting = lendObjects.flatMap { lendObject ->
                    lendObject.waitingList = lendObject.waitingList.map {
                        it.parenObjectId = lendObject.id
                        it
                    }
                    lendObject.waitingList
                }

                // Get all Current ObjectUsers
                val objectUsersCurrent = lendObjects.map { lendObject ->
                    lendObject.user?.parenObjectId = lendObject.id
                    lendObject.user
                }.filterNotNull()

                // Set DB
                database.users.insertAllUsers(*users.asDatabaseModel())
                database.objects.insertAllLendObjects(*lendObjects.asDatabaseModel())
                database.objectUsers.deleteFromObjectUser()
                database.objectUsers.insertAllObjectUsers(*objectUsersWaiting.asDatabaseModels())
                database.objectUsers.insertAllObjectUsers(*objectUsersCurrent.asDatabaseModels(true))

                GlobalScope.launch(Dispatchers.Main) {
                    succes.value = loggedInUser.fullname
                }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    error.value = response.message()
                }
            }
        }
    }
}