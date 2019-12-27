package com.ocean.ishareeconomy_android.network

import com.auth0.android.jwt.JWT
import com.ocean.ishareeconomy_android.models.LoginResponseObject
import java.util.*

/**
 * Help method that takes a string token and turns it into a login response object, if valid
 *
 * @return [LoginResponseObject?]
 */
fun jwtToLoginResponseObject(token: String): LoginResponseObject? {
    val jwt = JWT(token)
    if (jwt.expiresAt!! <=  Date()) {
        return null
    }
    return jwt.getClaim("user").asObject(LoginResponseObject::class.java)
}