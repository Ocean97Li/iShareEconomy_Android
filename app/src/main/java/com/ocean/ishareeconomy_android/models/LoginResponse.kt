package com.ocean.ishareeconomy_android.models

import com.google.gson.annotations.SerializedName

/**
 * Part of *models*.
 *
 * LoginResponse gets returned when a login correctly occurs
 * @property token holds the JWT token of the user
 */
class LoginResponse(val token: String)

/**
 * Part of *models*.
 *
 * LoginResponseObject is the result of the JWT parsing of the [LoginResponse.token]
 * @property id holds the JWT token of the user
 * @property username The logged in users full name, corresponds to [User.fullName]
 */
class LoginResponseObject(
    @SerializedName("_id")
    val id: String,
    val username: String
)