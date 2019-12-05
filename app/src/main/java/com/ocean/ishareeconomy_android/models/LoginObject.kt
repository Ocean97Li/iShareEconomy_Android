package com.ocean.ishareeconomy_android.models

/**
 * Part of *models*.
 *
 * LoginObject gets returned when a login correctly occurs
 * @property token holds the JWT token of the user
 * @property id holds the id of the user
 */
class LoginObject(val token: String, val id: String)