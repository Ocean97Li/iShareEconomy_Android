package com.ocean.ishareeconomy_android.models

/**
 * Part of *models*.
 *
 * LoginObject gets returned when a login correctly occurs
 * @property username holds the JWT token of the user
 * @property password holds the id of the user
 */
class LoginObject(val username: String, private val password: String)