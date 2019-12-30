package com.ocean.ishareeconomy_android.viewmodels

import com.ocean.ishareeconomy_android.models.ObjectOwner

open class OwnerObjectViewModel(private val owner: ObjectOwner) {
    val name = owner.name
}