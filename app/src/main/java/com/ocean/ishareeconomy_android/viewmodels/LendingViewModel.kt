package com.ocean.ishareeconomy_android.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.ocean.ishareeconomy_android.database.getDatabase
import com.ocean.ishareeconomy_android.database.IShareDataBase
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.repositories.RepositoryParams
import com.ocean.ishareeconomy_android.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Part of *viewmodels*.
 *
 * LendingViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available. It's also responsible for the removal of objects
 *
 *  * Inherits from [AndroidViewModel] so that it remains unaffected by rotations
 *  @property id: [String] the logged in user's id
 *  @property auth: [String] the logged in user's authentication JWT token
 *  @property viewModelJob: [SupervisorJob] job for all co-routines started by this ViewModel, cancel to cancel all
 *  @property viewModelScope: [CoroutineScope] the main scope for all co-routines launched by MainViewModel.
 *  @property dataBase: [IShareDataBase]
 *  @property repository: a [UserRepository] Singleton
 *  @property lending: the list of [LendingObject] that the user is sharing
 *
 *  @constructor AddLendingViewModel
 *  @param id: [String]
 *  @param auth: [String]
 *  @param application The application that this viewmodel is attached to, it's safe to hold a
 * reference to applications across rotation since Application is never recreated during actiivty
 * or fragment lifecycle events.
 */
class LendingViewModel(application: Application, val id: String, val auth: String) : AndroidViewModel(application) {

    private var viewModelJob = SupervisorJob()
    private var viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val dataBase = getDatabase(application)
    private val repository = UserRepository.getInstance(RepositoryParams(id,dataBase))

    val lending = repository.lending

    init {
        refreshUsers()
    }

    fun removeObject(lendObject: LendingObject) {
        if (viewModelJob.isCancelled) {
            viewModelJob = SupervisorJob()
            viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
        }
        viewModelScope.launch {
            repository.removeLendObject(id, lendObject.id, auth)
        }
    }

    private fun refreshUsers() {
        viewModelScope.launch {
            repository.refreshUsers(id, auth)
        }
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application, val id: String, val auth: String) : ViewModelProvider.Factory {
        @Throws(IllegalArgumentException::class)
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LendingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LendingViewModel(app, id, auth) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}