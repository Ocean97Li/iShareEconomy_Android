package com.ocean.ishareeconomy_android.lending

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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

    var lendingFragment =  LendingMasterFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lending)
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.lending_container, lendingFragment)
                    .commit()
        }

        window.enterTransition = null
        window.exitTransition = null

        nav_view.menu.getItem(0).isChecked = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            add_lending_object_button -> {
                // ADD Lendobject Add fragment
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.lending_top_nav_menu, menu)
        return true
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_lending -> {
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
}