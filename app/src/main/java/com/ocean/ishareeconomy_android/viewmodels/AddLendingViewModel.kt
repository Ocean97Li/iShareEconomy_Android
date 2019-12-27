package com.ocean.ishareeconomy_android.viewmodels

import android.app.Application
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.database.getDatabase
import com.ocean.ishareeconomy_android.database.IShareDataBase
import com.ocean.ishareeconomy_android.lending.OnShareListener
import com.ocean.ishareeconomy_android.lending.SelectedColor
import com.ocean.ishareeconomy_android.models.LendObjectType
import com.ocean.ishareeconomy_android.repositories.RepositoryParams
import com.ocean.ishareeconomy_android.repositories.UserRepository
import kotlinx.coroutines.*

/**
 * Part of *viewmodels*.
 *
 * The viewmodel that is used for the AddLending Screen, it enables the input of properties for
 * the creation of new LendingObject, is aware of changes via two-way databinding
 * and allows the creation when all conditions are satisfied.
 *
 * Inherits from [AndroidViewModel] so that it remains unaffected by rotations
 *  @property id: [String] the logged in user's id
 *  @property auth: [String] the logged in user's authentication JWT token
 *  @property viewModelJob: [SupervisorJob] job for all co-routines started by this ViewModel, cancel to cancel all
 *  @property viewModelScope: [CoroutineScope] the main scope for all co-routines launched by MainViewModel.
 *  @property dataBase: [IShareDataBase]
 *  @property repository: a [UserRepository] Singleton manages all the [User]
 *  @property type: [LendObjectType] the new object's type
 *  @property name: [String] the new object's name
 *  @property description: [String] the new object's description
 *  @property share: [Boolean] value continually updated to reflect whether the object can be created, or not
 *  @property colorSetter: [SelectedColor] the parent fragment, sets the type button background color
 *  @property navigateBack: [OnShareListener] the parent fragment, does the back navigation
 *
 *  @constructor AddLendingViewModel
 *  @param application: [Application]
 *  @param id: [String]
 *  @param auth: [String]
 *
 */
class AddLendingViewModel(application: Application, val id: String, private val auth: String) : AndroidViewModel(application)  {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val dataBase = getDatabase(application)
    private val repository = UserRepository.getInstance(RepositoryParams( id, dataBase))

    var type: LendObjectType? = null
        set(value) {
            field = value
            val enabled = !name.isNullOrEmpty() && !description.isNullOrEmpty() && type != null
            share.set(enabled)
        }

    var name: String? = null
        set(value) {
            field = value
            val enabled = !name.isNullOrEmpty() && !description.isNullOrEmpty() && type != null
            share.set(enabled)
        }
    var description: String? = null
        set(value) {
            field = value
            val enabled = !name.isNullOrEmpty() && !description.isNullOrEmpty() && type != null
            share.set(enabled)
        }

    var share = ObservableBoolean(false)

    var colorSetter: SelectedColor? = null
    var navigateBack: OnShareListener? = null

    /**
     * Cancel all co-routines when the ViewModel is cleared
     *
     * @return Unit
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Method that's responsible for setting the type
     * It also sets buttons color, indicating that it has been selected
     * this is delegated to parent fragment [colorSetter], which is known under the [SelectedColor] interface
     *
     * @param view the button was that clicked
     *
     * @return Unit
     */
    fun onTypeButtonClicked(view: View) {
        when(view.id) {
            R.id.tool_button -> {
                type = LendObjectType.Tool
            }
            R.id.service_button -> {
                type = LendObjectType.Service
            }
            R.id.transportation_button -> {
                type = LendObjectType.Transportation
            }
        }
        colorSetter?.setSelected(type!!)
    }

    /**
     * Method that's responsible for initiating the back navigation
     * if all conditions are met
     * it delegates to the parent fragment [navigateBack], which is known under the [OnShareListener] interface
     *
     * @param view the button was that clicked
     *
     * @return Unit
     */
    fun onShareBtnClick(view: View) {
        viewModelScope.launch {
            if(share.get()) {
                repository.addLendObject(id, auth, name!!, description!!, type!!.toString())
                GlobalScope.launch(Dispatchers.Main) {
                    navigateBack!!.navigateBackToMaster()
                }
            }
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