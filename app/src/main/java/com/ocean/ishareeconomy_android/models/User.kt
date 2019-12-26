package com.ocean.ishareeconomy_android.models

import com.google.gson.annotations.SerializedName

/**
 * Part of *models*.
 *
 * User object that is used to login and then fetch the correct user object
 * @property id the id of the user
 * @property firstName the user's firstname
 * @property lastName the user's lastname
 * @property username the user's username, used to login
 * @property address the user's full address: streetnumber, streetname, city, postalcode, country
 * @property rating the user's rating
 * @property distance the user's distance from the logged in user
 */
class User(
    @SerializedName("_id")
    val id: String,
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("lastname")
    val lastName: String,
    val username: String,
    val address: String,
    val rating: Int,
    val distance: Double,
    var lending: List<LendingObject>) {

    val fullName: String
        get() = "${firstName.capitalize()} ${lastName.capitalize()}"

}