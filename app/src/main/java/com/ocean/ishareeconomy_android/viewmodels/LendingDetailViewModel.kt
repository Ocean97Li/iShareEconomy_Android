package com.ocean.ishareeconomy_android.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocean.ishareeconomy_android.database.getDatabase
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.models.ObjectOwner
import com.ocean.ishareeconomy_android.models.ObjectUser
import com.ocean.ishareeconomy_android.models.User
import com.ocean.ishareeconomy_android.repositories.RepositoryParams
import com.ocean.ishareeconomy_android.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Part of *viewmodels*.
 *
 * LendingDetailViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available. It's also responsible for the removal of objects
 *
 *  * Inherits from [AndroidViewModel] so that it remains unaffected by rotations
 *  @property viewModelJob: [SupervisorJob] job for all co-routines started by this ViewModel, cancel to cancel all
 *  @property viewModelScope: [CoroutineScope] the main scope for all co-routines launched by MainViewModel.
 *
 *  @property lendingObject: [LendingObject] the object from which the details are displayed
 *  @property owner: [LendingObject.user] a [ObjectOwner] object, representing the creator [User].
 *  Automatically updated when a new [LendingObject] is passed.
 *  @property user: [LendingObject.user] is either null or a [ObjectUser] object, representing a usage.
 *  Automatically updated when a new [LendingObject] is passed.
 *  @property waitingList: [List]<[ObjectUser]>, represents all the [User]s that are waiting to use the object.
 *  Automatically updated when a new [LendingObject] is passed.
 *
 *  @constructor Creates a [LendingDetailViewModel]
 *  @param application: [Application] the activity's application, used to make the parent [AndroidViewModel] lifecycle aware
 *
 *

 */
class LendingDetailViewModel(application: Application, id: String): AndroidViewModel(application) {

    private val dataBase = getDatabase(application)
    private val repository = UserRepository.getInstance(RepositoryParams(id,dataBase))

    private var viewModelJob = SupervisorJob()
    private var viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val lending = repository.lending
    val using = repository.using
    val selectedLendObject = repository.selectedLendObject

    /**
     * Factory for constructing LendingDetailViewModel with parameters
     */
    class Factory(val app: Application, val id: String) : ViewModelProvider.Factory {
        @Throws(IllegalArgumentException::class)
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LendingDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LendingDetailViewModel(app, id) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

