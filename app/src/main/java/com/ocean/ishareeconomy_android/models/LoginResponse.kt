package com.ocean.ishareeconomy_android.models

import com.google.gson.annotations.SerializedName

/**
 * Part of *models*.
 *
 * LoginResponse gets returned when a login correctly occurs
 * @property token holds the JWT token of the user
 */
class LoginResponse(val token: String)

class LoginResponseObject(
    @SerializedName("_id")
    val id: String,
    val username: String
)