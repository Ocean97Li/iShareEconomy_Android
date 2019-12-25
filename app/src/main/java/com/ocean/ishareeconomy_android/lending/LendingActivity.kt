package com.ocean.ishareeconomy_android.lending

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.R.id.add_lending_object_button
import com.ocean.ishareeconomy_android.using.UsingActivity
import kotlinx.android.synthetic.main.activity_lending.*

/**
 * Part of *lending*.
 *
 * Activity responsible for displaying the lendobjects currently shared by the user
 * @property lendingFragment Builds the lending overview screen
 *
 */
class LendingActivity : AppCompatActivity() {

    val lendingFragment =  LendingMasterFragment()
    val addFragment = LendingAddFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lending)
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_layout, lendingFragment)
                    .commit()
        }

        window.enterTransition = null
        window.exitTransition = null

        nav_view.menu.getItem(0).isChecked = true
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_lending -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, lendingFragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_using -> {
                val intent = Intent(this, UsingActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                this.finish()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_requests -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun navigateToMaster() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, lendingFragment)
            .commit()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        /* Checks whether a hardware keyboard is available */
        if (newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_NO) {
            nav_view.visibility = View.GONE
        } else  {
            nav_view.visibility = View.VISIBLE
        }
    }
}