package com.ocean.ishareeconomy_android.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ocean.ishareeconomy_android.R

/**
 * Deel van *login*.
 *
 * Activy verantwoordelijk voor het inloggen van gebruikers
 * @property loginFragment Bouwt het inlogscherm op en bevat de achterliggende functionaliteit
 *
 */
class LoginActivity : AppCompatActivity() {
    val loginFragment = LoginFragment()

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