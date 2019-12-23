package com.ocean.ishareeconomy_android.viewmodels

import android.app.Application
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.database.getDatabase
import com.ocean.ishareeconomy_android.lending.OnShareListener
import com.ocean.ishareeconomy_android.lending.SelectedColor
import com.ocean.ishareeconomy_android.repositories.RepositoryParams
import com.ocean.ishareeconomy_android.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AddLendingViewModel(application: Application, val id: String, val auth: String) : AndroidViewModel(application)  {

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
    private val repository = UserRepository.getInstance(RepositoryParams( id, dataBase))

    var type: String? = null
        set(value) {
            field = value
            val enabled = !name.isNullOrEmpty() && !description.isNullOrEmpty() && !type.isNullOrEmpty()
            share.set(enabled)
        }

    var name: String? = null
        set(value) {
            field = value
            val enabled = !name.isNullOrEmpty() && !description.isNullOrEmpty() && !type.isNullOrEmpty()
            share.set(enabled)
        }
    var description: String? = null
        set(value) {
            field = value
            val enabled = !name.isNullOrEmpty() && !description.isNullOrEmpty() && !type.isNullOrEmpty()
            share.set(enabled)
        }

    var share = ObservableBoolean(false)

    var colorSetter: SelectedColor? = null
    var navigateBack: OnShareListener? = null

    init {
        viewModelScope.launch {
        }
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onTypeButtonClicked(view: View) {
        when(view.id) {
            R.id.tool_button -> {
                type = "tool"
                colorSetter?.setSelected(type!!)
            }
            R.id.service_button -> {
                type = "service"
                colorSetter?.setSelected(type!!)
            }
            R.id.transportation_button -> {
                type = "transportation"
                colorSetter?.setSelected(type!!)
            }
        }
    }

    fun onShareBtnClick(view: View) {
        viewModelScope.launch {
            if(share.get()) {
                repository.addLendObject(id, auth, name!!, description!!, type!!)
            }
            navigateBack!!.navigateBackToMaster()
        }
    }

    /**
     * Factory for constructing AddLendingViewModel with parameters
     */
    class Factory(val app: Application, val id: String, val auth: String) : ViewModelProvider.Factory {
        @Throws(IllegalArgumentException::class)
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddLendingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddLendingViewModel(app, id, auth) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}