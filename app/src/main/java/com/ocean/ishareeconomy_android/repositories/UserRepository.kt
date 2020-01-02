package com.ocean.ishareeconomy_android.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ocean.ishareeconomy_android.database.entities.asDomainModel
import com.ocean.ishareeconomy_android.database.IShareDataBase
import com.ocean.ishareeconomy_android.database.relationships.asDomainModel
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.models.ObjectOwner
import com.ocean.ishareeconomy_android.models.User
import com.ocean.ishareeconomy_android.network.Network
import com.ocean.ishareeconomy_android.utils.asDatabaseModel
import com.ocean.ishareeconomy_android.utils.asDatabaseModels
import com.ocean.ishareeconomy_android.utils.ReusableRepositorySingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Part of *login*.
 *
 * Parameter bundle for [UserRepository] Singleton
 *  @property loggedInUserId the logged in user's id, needed for almost all API calls
 *  @property database main database object, needed to store data locally
 */
class RepositoryParams(val loggedInUserId: String, val database: IShareDataBase)

/**
 * Part of *login*.
 *
 * Repository responsible for managing all the domain related network requests.
 * [UserRepository] is the only domain related repository because
 * the [User] object is the central access point of the domain
 *
 * @property loggedInUserId: [String] the logged in user's id, needed for almost all API calls
 * @property database: [IShareDataBase] main database object, needed to store data locally
 * @property success: [MutableLiveData<String>] observable where login success message is pushed
 * @property error: [MutableLiveData<String>] observable where error message is pushed
 * @property _loggedInUser the logged in [User]
 *
 * @property loggedInUser from the database
 * @property users all the users from the database
 * @property lending the list [LendingObject] the logged in user is sharing
 * @property using the list [LendingObject] the logged in user is using
 *
 * @constructor creates the [UserRepository] instance, private because of the Singleton pattern
 * @param params is [RepositoryParams] object because generic [ReusableRepositorySingleton] class
 * only supports one parameter
 */
class UserRepository private constructor(params: RepositoryParams) {

    private val loggedInUserId: String = params.loggedInUserId
    private val  database: IShareDataBase = params.database

    /**
     * [ReusableRepositorySingleton] enables Singleton Pattern with params
     */
    companion object : ReusableRepositorySingleton<UserRepository, RepositoryParams>(::UserRepository)

    private val success = MutableLiveData<String>()
    private val error = MutableLiveData<String>()
    private var _loggedInUser: User? = null

    val loggedInUser: LiveData<User> = Transformations.map(database.users.getAllUsers()) {
        it.asDomainModel().map {
                user ->
            user.lending = database.objects.lendingObjectsForUserById(user.id).asDomainModel()
            _loggedInUser = user
            user
        }.find { user -> user.id == loggedInUserId }
    }

    val users: LiveData<List<User>> = Transformations.map(database.users.getAllUsers()) {
        it.asDomainModel().map {
            user ->
            user.lending = database.objects.lendingObjectsForUserById(user.id).asDomainModel()
            user.using = database.objects.usingObjectsForUserById(user.id).asDomainModel()
            user.lending.map { lendObject ->
                if (lendObject.user != null  && _loggedInUser != null) {
                    lendObject.user!!.distance = _loggedInUser!!.distance
                }
            }
            user.using.map { lendObject ->
                if (lendObject.user != null  && _loggedInUser != null) {
                    lendObject.user!!.distance = _loggedInUser!!.distance
                }
            }
            user
        }
    }

    val lending: LiveData<List<LendingObject>> =
        Transformations.map(database.objects.getLendingObjectsFromUserById(loggedInUserId)) {
        it.asDomainModel().map { lendObject ->
            if (lendObject.user != null && _loggedInUser != null) {
                lendObject.user!!.distance = _loggedInUser!!.distance
            }
            lendObject
        }
    }

    val using: LiveData<List<LendingObject>> =
        Transformations.map(database.objects.getObjectsCurrentlyUsedByUser(loggedInUserId)) {
        it.asDomainModel().map { lendObject ->
            if (lendObject.user != null && _loggedInUser != null) {
                lendObject.user!!.distance = _loggedInUser!!.distance
            }
            lendObject
        }
    }

    val selectedLendObject: MutableLiveData<LendingObject> = MutableLiveData()


    /**
     * Method that makes a network call to POST a new [LendingObject] to the user's lending list
     * and on success stores it in the DB
     */
    suspend fun addLendObject(id: String, auth: String, name: String, description: String, type: String) {
        withContext(Dispatchers.IO) {
            val lendingObject = LendingObject(
                id = "",
                name = name,
                description = description,
                type = type,
                owner = ObjectOwner(_loggedInUser!!.id, _loggedInUser!!.fullName),
                user = null,
                waitingList = emptyList()
            )
            val response = Network.lending.postLendObjectAsync(id, auth, lendingObject).await()
            if (response.isSuccessful) {
               response.body()?.let {
                   database.objects.insertAllLendObjects(it.asDatabaseModel())
               }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    error.value = response.message()
                }
            }
        }
    }

    /**
     * Method that makes a network call to DELETE an existing [LendingObject] from the user's lending list
     * and on success stores remove it from the DB
     */
    suspend fun removeLendObject(userId: String, objectId: String, auth: String) {
        withContext(Dispatchers.IO) {
            val response = Network.lending.deleteLendObjectAsync(userId, objectId, auth).await()
            if (response.isSuccessful || response.body() is LendingObject ) {
                response.body()?.let {
                    database.objects.deleteObjectById(it.id)
                }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    error.value = response.message()
                }
            }
        }
        this.refreshUsers(userId,auth)
    }

    /**
     * Method that makes a network GET call to fetch the total list of [User], starting with the logged in,
     * [User], ordered by [User.distance] and store it in the DB
     */
    suspend fun refreshUsers(id: String, auth: String) {
        withContext(Dispatchers.IO) {
            val response = Network.users.getUsersAsync(id, auth).await()
            if (response.isSuccessful) {
                // Get the users
                var users = response.body()!!

                // Get the logged in user
                _loggedInUser = users.find { user -> user.id == id }!!

                // Get all LendObjects
                val lendObjects = users.flatMap { user -> user.lending }

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
                    lendObject.user?.distance = _loggedInUser!!.distance
                    lendObject.user
                }.filterNotNull()

                // Set DB
                database.users.insertAllUsers(*users.asDatabaseModel())
                database.objects.insertAllLendObjects(*lendObjects.asDatabaseModel())
                database.objectUsers.deleteFromObjectUser()
                database.objectUsers.insertAllObjectUsers(*objectUsersWaiting.asDatabaseModels())
                database.objectUsers.insertAllObjectUsers(*objectUsersCurrent.asDatabaseModels(true))

                GlobalScope.launch(Dispatchers.Main) {
                    success.value = _loggedInUser!!.fullName
                }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    error.value = response.message()
                }
            }
        }
    }
}