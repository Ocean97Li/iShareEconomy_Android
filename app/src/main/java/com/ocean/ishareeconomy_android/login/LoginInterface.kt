package com.ocean.ishareeconomy_android.login

import com.ocean.ishareeconomy_android.viewmodels.LoginViewModel

/**
 * An interface that defines the methods the [LoginFragment] should implement for the [LoginViewModel]
 */
interface LoginInterface {
    fun showToastWith(textId: Int, lengthId: Int)
    fun showToastWith(text: String, lengthId: Int)
    fun getString(id: Int): String
    fun navigateToMain()
    fun getToken(): String
    fun getUsername(): String
    fun getPassword(): String
    fun setToken(token: String)
    fun setUsername(username: String)
    fun setPassword(password: String)
}