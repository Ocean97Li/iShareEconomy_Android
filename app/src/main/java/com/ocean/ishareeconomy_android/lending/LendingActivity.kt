package com.ocean.ishareeconomy_android.lending

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ocean.ishareeconomy_android.R
import kotlinx.android.synthetic.main.activity_lending.*

/**
 * Part of *lending*.
 *
 * Activity responsible voor het inloggen van gebruikers
 * @property loginFragment Bouwt het inlogscherm op en bevat de achterliggende functionaliteit
 *
 */
class LendingActivity : AppCompatActivity() {

    var lendingFragment =  LendingMasterFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lending)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.lending_container, lendingFragment)
                    .commit()
        }
    }
}