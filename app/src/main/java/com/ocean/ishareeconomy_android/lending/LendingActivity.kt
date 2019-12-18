package com.ocean.ishareeconomy_android.lending

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.lending_container, lendingFragment)
                    .commit()
        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_lending -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_using -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_requests -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}