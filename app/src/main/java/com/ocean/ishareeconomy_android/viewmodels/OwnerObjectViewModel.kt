package com.ocean.ishareeconomy_android.viewmodels

import com.ocean.ishareeconomy_android.models.ObjectOwner

open class OwnerObjectViewModel(owner: ObjectOwner) {
    val name = owner.name
}