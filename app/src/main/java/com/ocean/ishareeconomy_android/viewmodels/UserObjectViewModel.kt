package com.ocean.ishareeconomy_android.viewmodels

import com.ocean.ishareeconomy_android.models.ObjectUser

class UserObjectViewModel(val user: ObjectUser? = null) {
    val name = user?.name
    val froToDate = "From ${user?.from} to ${user?.to}"
    val distance = 0.0
    val distanceString = "$distance km"
}