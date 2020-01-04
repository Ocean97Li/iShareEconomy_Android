package com.ocean.ishareeconomy_android.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.lending.LendingActivity
import com.ocean.ishareeconomy_android.models.LoginObject
import com.ocean.ishareeconomy_android.models.LoginResponse
import com.ocean.ishareeconomy_android.models.User
import com.ocean.ishareeconomy_android.network.Network
import com.ocean.ishareeconomy_android.network.jwtToLoginResponseObject
import com.ocean.ishareeconomy_android.repositories.LoginAPI
import kotlinx.android.synthetic.main.fragment_login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Part of *login*.
 *
 * Fragment that supports inputs for username and password and login button
 *
 * @property usernameInput the [EditText] that is used for the username
 * @property passwordInput the [EditText] that is used for the password
 *
 * @property usernameInputLayout the [usernameInputLayout] for username error messages
 * @property passwordInputLayout the [passwordInputLayout] for password error messages
 *
 */
class LoginFragment : Fragment() {

    // storage
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor

    // actual text inputs
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText

    // input layouts for the text inputs
    private lateinit var usernameInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout


    /**
     * Method called when the fragment is created
     *
     * @return [Unit]
     */
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = context!!.getSharedPreferences("userdetails", Context.MODE_PRIVATE)
        spEditor = sharedPreferences.edit()

        // Automatically login when non-expired token is present
        val token = sharedPreferences.getString(getString(R.string.sp_user_token), "")
        if ( token != null && token.isNotEmpty()) {
            // Fetch the users and login
            fetchUsersAndLogin(token)
        }
    }

    /**
     * Method called when the view is created
     *
     * @return [Unit]
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // set the class attributes to the corresponding elements in the xml
        usernameInput = view.username_input
        passwordInput = view.password_input

        usernameInputLayout = view.username_inputLayout as TextInputLayout
        passwordInputLayout = view.password_inputLayout as TextInputLayout

        readSharedPreferences()

        view.login_btn.setOnClickListener {
            when {
                // if not valid set error message for username
                !validUsername() -> usernameInputLayout.error = getString(R.string.error_username)
                // remove the error message for username
                else -> usernameInputLayout.error = null
            }

            when {
                // if not valid set error message for password
                !validPassword() -> passwordInputLayout.error = getString(R.string.error_password)
                // remove the error message for password
                else -> passwordInputLayout.error = null
            }

            // if username and password valid attempt to login
            when {
                validUsername() && validPassword() -> attemptLogin()
            }
        }

        return view
    }

    /**
     * Help method that checks if the username input is filled in
     *
     * @return [Boolean]
     */
    private fun validUsername(): Boolean {
        return usernameInput.text != null && usernameInput.text.isNotBlank()
    }

    /**
     * Help method that checks if the password input is filled in
     *
     * @return [Boolean]
     */
    private fun validPassword(): Boolean {
        return passwordInput.text != null && passwordInput.text.isNotBlank()
    }

    /**
     * Help method that fills the login inputs with the stored username and password from the sharedPreferences
     *
     *  @return [Unit]
     */
    private fun readSharedPreferences() {
        val username = sharedPreferences.getString(getString(R.string.sp_login_username), "")
        val password = sharedPreferences.getString(getString(R.string.sp_login_password), "")

        // set the input with the username and password that was read from the sharedPreferences
        usernameInput.setText(username)
        passwordInput.setText(password)
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
                    val toast = Toast.makeText(context, "${getString(R.string.welcome)} ${loggedInUser.fullName}!", Toast.LENGTH_LONG)
                    toast.show()
                    // goto the HomeActivity
                    val intent = Intent(activity, LendingActivity::class.java)
                    startActivity(intent)
                    activity?.finish()

                } else {
                    Toast.makeText(context, getString(R.string.wrong_login_credentials), Toast.LENGTH_LONG).show()
                }
            }

            // backend can't be reached
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                println(call)
                println(t.message)
                val toast = Toast.makeText(context, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG)
                val v = toast.view.findViewById(android.R.id.message) as TextView
                v.gravity = Gravity.CENTER
                toast.show()
            }
        })
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
        // reset error messages
        usernameInputLayout.error = null
        passwordInputLayout.error = null

        // store values at the time of the login attempt
        val username = usernameInput.text.toString()
        val password = passwordInput.text.toString()

        val user = LoginObject(username, password)

        val call = LoginAPI.repository.login(user)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {

                    // Store the login Token in SharedPreferences
                    val token = response.body()?.token
                    if (token != null) {
                        spEditor.putString(getString(R.string.sp_user_token), token.toString())
                        spEditor.putString(getString(R.string.sp_login_username), username)
                        spEditor.putString(getString(R.string.sp_login_password), password)
                        spEditor.apply()

                        fetchUsersAndLogin(token)
                    }

                } else {
                    Toast.makeText(context, getString(R.string.wrong_login_credentials), Toast.LENGTH_LONG).show()
                }
            }

            // backend can't be reached
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                println(call)
                println(t.message)
                val toast = Toast.makeText(context, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG)
                val v = toast.view.findViewById(android.R.id.message) as TextView
                v.gravity = Gravity.CENTER
                toast.show()
            }
        })
    }
}