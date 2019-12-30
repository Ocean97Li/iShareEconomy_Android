package com.ocean.ishareeconomy_android.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.ocean.ishareeconomy_android.database.getDatabase
import com.ocean.ishareeconomy_android.repositories.RepositoryParams
import com.ocean.ishareeconomy_android.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * LendingViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 *
 * @param application The application that this viewmodel is attached to, it's safe to hold a
 * reference to applications across rotation since Application is never recreated during actiivty
 * or fragment lifecycle events.
 *
 */
class UsingViewModel(application: Application, val id: String, private val auth: String) : AndroidViewModel(application) {

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val dataBase = getDatabase(application)
    private val repository = UserRepository.getInstance(RepositoryParams(id,dataBase))

    init {
        viewModelScope.launch {
            repository.refreshUsers(id, auth)
        }
    }

    val using = repository.using

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
            if (modelClass.isAssignableFrom(UsingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UsingViewModel(app, id, auth) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}