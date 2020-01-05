package com.ocean.ishareeconomy_android.viewmodels

import com.ocean.ishareeconomy_android.models.ObjectUser
/**
 * Part of *viewmodels*.
 *
 * The viewmodel used to draw a card representing the [ObjectUser] of a lendobject
 *
 * @property name of the [ObjectUser]
 *
 * @param user an object of the type [ObjectUser]
 * @param distance of the user's address to the logged in user's
 * @param fromToDate the period in which [ObjectUser] will be the current user
 * @param distanceString the [distance] in km
 */
class UserObjectViewModel(val user: ObjectUser? = null, val distance: Double? = null) {
    val name = user?.name
    val fromToDate = "From ${user?.from} to ${user?.to}"
    val distanceString = "$distance km"
}