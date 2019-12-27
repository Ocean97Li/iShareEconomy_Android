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
 * @property lendingFragment: [LendingMasterFragment] Builds the lending overview screen
 * @property addFragment: [LendingAddFragment] Builds the add lending object screen
 * @property onNavigationItemSelectedListener: [BottomNavigationView.OnNavigationItemSelectedListener]
 * provides the implementation of BottomNavigation
 */
class LendingActivity : AppCompatActivity() {

    private val lendingFragment =  LendingMasterFragment()
    private val addFragment = LendingAddFragment()
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

    /**
     * Method that is called when the activity is created
     *
     * @param savedInstanceState: [Bundle?]
     *
     * @return [Unit]
     **/
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

    /**
     * Method that navigates to the lending objects overview fragment
     *
     * @return [Unit]
     **/
    fun navigateToMaster() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, lendingFragment)
            .commit()
    }

    /**
     * Method that navigates to the add lending objects screen fragment
     *
     * @return [Unit]
     **/
    fun navigateToAdd() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, addFragment)
            .commit()
    }
}