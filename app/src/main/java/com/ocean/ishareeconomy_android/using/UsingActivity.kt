package com.ocean.ishareeconomy_android.using

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.lending.LendingActivity

/**
 * Part of *lending*.
 *
 * Activity responsible for showing the lendobjects currently used by the user
 * @property usingFragment builds the using overview screen
 * @property onNavigationItemSelectedListener: [BottomNavigationView.OnNavigationItemSelectedListener]
 * provides the implementation of BottomNavigation
 */
class UsingActivity : AppCompatActivity() {

    private var usingFragment =  UsingMasterFragment()
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_lending -> {
                val intent = Intent(this, LendingActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                this.finish()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_using -> {
//                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_requests -> {
//                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    /**
     * Called when the activity is created
     *
     * @param savedInstanceState: [Bundle?]
     *
     * @return [Unit]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_using)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_layout, usingFragment)
                    .commit()
        }

        window.enterTransition = null
        window.exitTransition = null

        navView.menu.getItem(1).isChecked = true
    }
}
