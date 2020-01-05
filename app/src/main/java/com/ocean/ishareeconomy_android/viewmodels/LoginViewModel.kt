package com.ocean.ishareeconomy_android.viewmodels

import android.app.Application
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.login.LoginInterface
import com.ocean.ishareeconomy_android.models.LoginObject
import com.ocean.ishareeconomy_android.models.LoginResponse
import com.ocean.ishareeconomy_android.models.User
import com.ocean.ishareeconomy_android.network.Network
import com.ocean.ishareeconomy_android.network.jwtToLoginResponseObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Part of *viewmodels*.
 *
 * The viewmodel that is used to display the lending screen.
 * It also contains all the domain logic concerning the login.
 * Being a viewmodel is delegates certain UI-related task back to the parent fragment,
 * which it knows under as [loginInterface]
 *
 * @property username the [String] in the username input field
 * @property password the [String] in the password input field
 * @property usernameErrorMessage the error message which needs to be set when [username] remains empty
 * @property passwordErrorMessage the error message which needs to be set when [password] remains empty
 * @property loginInterface the parent fragment which handles certain UI-related tasks
 *
 * @param application used to instantiate the [AndroidViewModel] which is lifecycle aware and does not reset on rotate

 */
class LoginViewModel(
    application: Application,
    private val loginInterface: LoginInterface
) : AndroidViewModel(application){

    var username: String = ""
    var password: String = ""

    var usernameErrorMessage = MutableLiveData<String>()
    var passwordErrorMessage = MutableLiveData<String>()

    init {
        username = loginInterface.getUsername()
        password = loginInterface.getPassword()
        // Automatically login when non-expired token is present
        val token = loginInterface.getToken()
        if (token.isNotEmpty()) {
            // Fetch the users and login
            fetchUsersAndLogin(token)
        }
    }

    /**
     * The method which is called on button click and starts the whole login process
     */
    fun onLoginButtonClick(view: View) {
        if (view.id == R.id.login_btn) {
            if (validateLogin()) {
                attemptLogin()
            }
        }
    }

    /**
     * The method which validates the login params and sets the error messages if need be
     */
    private fun validateLogin(): Boolean {

        when {
            // if not valid set error message for username
            !validUsername() -> usernameErrorMessage.postValue(loginInterface.getString(R.string.error_username))
            // remove the error message for username
            else -> usernameErrorMessage.postValue(null)
        }

        when {
            // if not valid set error message for password
            !validPassword() -> passwordErrorMessage.postValue(loginInterface.getString(R.string.error_password))
            // remove the error message for password
            else -> passwordErrorMessage.postValue(null)
        }

        // if username and password valid then the login is valid
        return validUsername() && validPassword()
    }

    /**
     * Helper method that checks if the username input is filled in
     *
     * @return [Boolean]
     */
    private fun validUsername(): Boolean {
        return username.isNotBlank()
    }

    /**
     * Helper method that checks if the password input is filled in
     *
     * @return [Boolean]
     */
    private fun validPassword(): Boolean {
        return password.isNotBlank()
    }

    /**
     * Method that attempts a (first) login;
     * first attempt is made to get a jwt authorisation token,
     * then the token is parsed using the external auth0 JWT library,
     * when the id of the user is found, this id is used to fetch the user objects,
     * including the logged in user
     *
     * @return [Unit]
     *
     */
    private fun attemptLogin() {

        val user = LoginObject(username, password)

        val call = Network.login.login(user)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {

                    // Store the login Token in SharedPreferences
                    val token = response.body()?.token
                    if (token != null) {
                        loginInterface.setToken(token.toString())
                        loginInterface.setUsername(username)
                        loginInterface.setPassword(password)

                        fetchUsersAndLogin(token)
                    }

                } else {
                    loginInterface.showToastWith(
                        R.string.wrong_login_credentials,
                        Toast.LENGTH_LONG
                    )
                }
            }

            // backend can't be reached
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                println(call)
                println(t.message)
                loginInterface.showToastWith(R.string.something_went_wrong_login, Toast.LENGTH_LONG)
            }
        })
    }

    /**
     * Method that takes a auth token and does a fetch of the all [User] objects, including the logged in [User]
     * if successful the user is logged in and taken to the next activity
     *
     * @param token the [String] that is returned and stored in the [SharedPreferences] when logging in.
     *
     * @return [Unit]
     */
    private fun fetchUsersAndLogin(token: String) {
        // Decode the token into a user's LoginResponseObject
        val userResponse = jwtToLoginResponseObject(token) ?: return
        // Prepare token for the request header
        val requestToken = "Bearer $token"


        // Fetch the actual Users
        val call2 = Network.users.getUsers(userResponse.id, requestToken)
        call2.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    // Get the users
                    val users = response.body()!!
                    // Get the logged in user
                    val index = users.indexOfFirst { user -> user.id == userResponse.id }
                    val loggedInUser = users[index]
                    // Display toast
                    val text =
                        "${loginInterface.getString(R.string.welcome)} ${loggedInUser.fullName}!"
                    loginInterface.showToastWith(text, Toast.LENGTH_SHORT)
                    // goto the actual application
                    loginInterface.navigateToMain()

                } else {
                    loginInterface.showToastWith(
                        R.string.wrong_login_credentials,
                        Toast.LENGTH_LONG
                    )
                }
            }

            // backend can't be reached
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                println(call)
                println(t.message)
                loginInterface.showToastWith(R.string.something_went_wrong_login, Toast.LENGTH_LONG)
            }
        })
    }

    /**
     * Factory for constructing a [LoginViewModel] with parameters
     *
     * @param app the application, used for the construction of the [AndroidViewModel] superclass
     *  which is lifecycle aware.
     * @param loginInterface the parent fragment of type [LoginInterface] which does all the UI related stuff
     *
     * @return [ViewModelProvider.Factory]
     */
    class Factory(val app: Application, private val loginInterface: LoginInterface) : ViewModelProvider.Factory {
        @Throws(IllegalArgumentException::class)
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(app, loginInterface) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}