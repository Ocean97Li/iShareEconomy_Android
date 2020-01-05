package com.ocean.ishareeconomy_android.login

import com.ocean.ishareeconomy_android.viewmodels.LoginViewModel

/**
 * An interface that defines the methods the [LoginFragment] should implement for the [LoginViewModel]
 */
interface LoginInterface {
    /**
     * Shows a customized toast message
     * @param textId the id of the text to be displayed
     * @param lengthId the length the toast should be displayed
     *
     * @return [Unit]
     */
    fun showToastWith(textId: Int, lengthId: Int)
    /**
     * Shows a customized toast message
     * @param text the literal text to be displayed
     * @param lengthId the length the toast should be displayed
     *
     * @return [Unit]
     */
    fun showToastWith(text: String, lengthId: Int)
    /**
     * Fetches a [String] value from the resources
     * @param id the string id
     *
     * @return [String] the string, possibly empty when no match
     */
    fun getString(id: Int): String
    /**
     * Navigates to the master fragment
     *
     * @return [Unit]
     */
    fun navigateToMain()
    /**
     * Fetches the auth token from the SharedPrefs
     *
     * @return [String] token, possibly empty when no match
     */
    fun getToken(): String
    /**
     * Fetches the logged in username from the SharedPrefs
     *
     * @return [String] the username, possibly empty when no match
     */
    fun getUsername(): String
    /**
     * Fetches the logged in user's password from the SharedPrefs
     *
     * @return [String] the password, possibly empty when no match
     */
    fun getPassword(): String
    /**
     * Sets the logged in user's token in the SharedPrefs
     *
     * @return [Unit]
     */
    fun setToken(token: String)
    /**
     * Sets the logged in user's username in the SharedPrefs
     *
     * @return [Unit]
     */
    fun setUsername(username: String)
    /**
     * Sets the logged in user's password in the SharedPrefs
     *
     * @return [Unit]
     */
    fun setPassword(password: String)
}