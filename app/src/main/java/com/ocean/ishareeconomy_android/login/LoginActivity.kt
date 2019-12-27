package com.ocean.ishareeconomy_android.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ocean.ishareeconomy_android.R

/**
 * Part of *login*.
 *
 * Activity responsible for logging in users
 * @property loginFragment builds the actual screen and contains the underlying functionality
 *
 */
class LoginActivity : AppCompatActivity() {
    private val loginFragment = LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.login_container, loginFragment)
                    .commit()
        }
    }
}