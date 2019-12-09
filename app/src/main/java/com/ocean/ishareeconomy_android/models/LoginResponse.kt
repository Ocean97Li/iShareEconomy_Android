package com.ocean.ishareeconomy_android.models

import android.util.Base64

/**
 * Part of *models*.
 *
 * LoginResponse gets returned when a login correctly occurs
 * @property token holds the JWT token of the user
 */
class LoginResponse(val token: String)

class LoginResponseObject(val _id: String, val username: String)