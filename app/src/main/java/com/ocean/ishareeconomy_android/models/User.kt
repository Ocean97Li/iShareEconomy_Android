package com.ocean.ishareeconomy_android.models

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName

/**
 * Part of *models*.
 *
 * User object that is used to login and then fetch the correct user object
 * @property id the [String] id of the user
 * @property firstName the user's first name [String]
 * @property lastName the user's last name [String]
 * @property username the user's username [String], used to login
 * @property address the user's full address [String]: street number, street name, city, postal code, country
 * @property rating the user's rating [Int]
 * @property distance the user's distance [Double] from the logged in user
 * @property fullName the user's full name [String] composed of first and last names capitalized
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
    var lending: List<LendingObject>,
    var using: List<LendingObject>
    ) {

    val fullName: String
        @SuppressLint("DefaultLocale")
        get() = "${firstName.capitalize()} ${lastName.capitalize()}"

}