package com.ocean.ishareeconomy_android.viewmodels

import com.ocean.ishareeconomy_android.models.ObjectUser

class UserObjectViewModel(private val user: ObjectUser) {
    val name = user.name
    val froToDate = "From ${user.from} to ${user.to}"
    val distance = "${user.distance} km"
}