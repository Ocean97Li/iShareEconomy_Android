package com.ocean.ishareeconomy_android.models

/**
 * Part of *models*.
 *
 * User object that is used to login and then fetch the correct user object
 * @property id the id of the user
 * @property firstname the user's firstname
 * @property lastname the user's lastname
 * @property username the user's username, used to login
 * @property address the user's full address: streetnumber, streetname, city, postalcode, country
 * @property rating the user's rating
 * @property distance the user's distance from the logged in user
 */
class User(
    val id: String,
    val firstname: String,
    val lastname: String,
    val username: String,
    val address: String,
    val rating: String,
    val distance: Double) {

    val fullname: String
        get() = "$firstname $lastname"

}