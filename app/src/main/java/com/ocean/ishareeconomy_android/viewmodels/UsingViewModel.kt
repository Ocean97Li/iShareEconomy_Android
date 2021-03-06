package com.ocean.ishareeconomy_android.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocean.ishareeconomy_android.database.getDatabase
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.repositories.RepositoryParams
import com.ocean.ishareeconomy_android.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 *
 *
 * LendingViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 *
 * @param application The application that this viewmodel is attached to, it's safe to hold a
 * reference to applications across rotation since Application is never recreated during activity
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
            if (using.value.isNullOrEmpty()) {
                repository.selectedLendObject.postValue(null)
            } else {
                repository.selectedLendObject.postValue(using.value!![0])
            }
        }
        viewModelScope.launch {
            repository.refreshUsers(id, auth)
        }
    }

    val using = repository.using

    fun selectObject(lendObject: LendingObject?) {
        repository.selectedLendObject.postValue(lendObject)
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     *
     * @return [Unit]
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     *
     * @return [ViewModelProvider.Factory]
     */
    class Factory(val app: Application, val id: String, private val auth: String) : ViewModelProvider.Factory {
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