package com.ocean.ishareeconomy_android.lending

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.using.UsingActivity
import kotlinx.android.synthetic.main.activity_lending.*
import kotlinx.android.synthetic.main.fragment_add_lending.*

/**
 * Part of *lending*.
 *
 * Activity responsible for displaying the lendobjects currently shared by the user
 * @property lendingMasterFragment: [LendingMasterFragment] Builds the lending overview screen
 * @property addFragment: [LendingAddFragment] Builds the add lending object screen
 * @property masterDetail: [Boolean], determines whether the screen is large enough that
 * master detail views should be displayed at the same time
 * @property onNavigationItemSelectedListener: [BottomNavigationView.OnNavigationItemSelectedListener]
 * provides the implementation of BottomNavigation
 */
class LendingActivity : AppCompatActivity() {

    // Fragments
    private val lendingMasterFragment = LendingMasterFragment()
    private var lendingDetailFragment = LendingDetailFragment()
    private val addFragment = LendingAddFragment()

    // Master Detail View Switch
    var masterDetail = false

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_lending -> {
                    navigateToMaster()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_using -> {
                    val intent = Intent(this, UsingActivity::class.java)
                    startActivity(
                        intent,
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
                    )
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
        nav_view_lending.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (master_detail_lending != null) {
            masterDetail = true
        }

        if (savedInstanceState == null) {
            configureMasterDetailView()
        }

        window.enterTransition = null
        window.exitTransition = null

        nav_view_lending.menu.getItem(0).isChecked = true
    }

    /**
     * Helper Method that sets up the Master Detail view.
     * for tablets and larger screen size devices both master and detail are displayed.
     * for smaller screen sizes they are displayed separately.
     *
     * @param replace Determines whether the fragments should be initialised (by default)
     * or replaced ([replace] = true)
     *
     * @return [Unit]
     **/
    private fun configureMasterDetailView(replace: Boolean = false) {

        if (!masterDetail) {
            return
        }

        val transaction = supportFragmentManager.beginTransaction()

        if (replace) {
            if (add_lending_container != null) {
                transaction.replace(addFragment.id, lendingMasterFragment)
                frame_layout_details_lending!!.visibility = View.VISIBLE
            }
        } else {
            transaction.add(R.id.frame_layout_master_lending, lendingMasterFragment)
            transaction.add(R.id.frame_layout_details_lending, lendingDetailFragment)
        }

        transaction.commit()
    }

    /**
     * Method that navigates to the lending objects overview fragment
     *
     * @return [Unit]
     **/
    fun navigateToMaster() {
        configureMasterDetailView(true)
    }

    /**
     * Method that navigates to the detail screen fragment
     *
     * @return [Unit]
     **/
    private fun navigateToDetail() {
        if (!masterDetail) {
            return
        }

        lendingDetailFragment = LendingDetailFragment()
    }

    /**
     * Method that navigates to the add lending objects screen fragment
     *
     * @return [Unit]
     **/
    fun navigateToAdd() {
        if (!masterDetail) {
            return
        }

        frame_layout_details_lending!!.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_master_lending, addFragment).commit()

    }

    /**
     * Method that navigates to the detail view when an list object in the [lendingMasterFragment]
     * is clicked. It sets the blank [LendingDetailFragment], which will be updated on initialisation by the repo
     *
     * @return [Unit]
     **/
    fun onDetailClick() {
        navigateToDetail()
    }
}