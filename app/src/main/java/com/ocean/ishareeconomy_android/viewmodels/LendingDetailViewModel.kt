package com.ocean.ishareeconomy_android.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.ocean.ishareeconomy_android.database.getDatabase
import com.ocean.ishareeconomy_android.database.IShareDataBase
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.models.ObjectOwner
import com.ocean.ishareeconomy_android.models.ObjectUser
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
 *  reference to applications across rotation since Application is never recreated during actiivty
 *  or fragment lifecycle events.
 */
class LendingDetailViewModel {

    private var viewModelJob = SupervisorJob()
    private var viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val lendingObject = MutableLiveData<LendingObject>()

    lateinit var owner: ObjectOwner
    var user: ObjectUser? = null
    var waitingList: List<ObjectUser> = emptyList()

    init {
        lendingObject.observeForever {
            owner = it.owner
            user = it.user
            waitingList = it.waitingList
        }
    }
}