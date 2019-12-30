package com.ocean.ishareeconomy_android.lending

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.models.LendingObject
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
    private val lendingMasterFragment =  LendingMasterFragment()
    private val lendingDetailFragment = LendingDetailFragment()
    private val addFragment = LendingAddFragment()

    // Master Detail View Switch
    var masterDetail = false

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        lendingMasterFragment.refresh()
        when (item.itemId) {
            R.id.navigation_lending -> {
                navigateToMaster()
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

        if (master_detail != null) {
            masterDetail = true
        }

        if (savedInstanceState == null) {
            configureMasterDetailView()
        }

        window.enterTransition = null
        window.exitTransition = null

        nav_view.menu.getItem(0).isChecked = true
    }

    /**
     * Helper Method that sets up the Master Detail view.
     * for tablets and larger screen size devices both master and detail are displayed.
     * for smaller screen sizes they are displayed separately.
     *
     * @param replace: [Boolean], determines whether the fragments should be initialised (by default)
     * or replaced ([replace] = true)
     *
     * @return [Unit]
     **/
    private fun configureMasterDetailView(replace: Boolean = false) {

        val transaction = supportFragmentManager.beginTransaction()

        if (masterDetail) {

            if (replace) {
                if (add_lending_container != null) {
                    transaction.replace(addFragment.id, lendingMasterFragment)
                    frame_layout_details_lending!!.visibility = View.VISIBLE
                }
            } else {
                transaction.add(R.id.frame_layout_master_lending, lendingMasterFragment)
                transaction.add(R.id.frame_layout_details_lending, lendingDetailFragment)
            }

        } else {
            if (replace) {
                transaction.replace(R.id.frame_layout_container_lending, lendingMasterFragment)
            } else {
                transaction.add(R.id.frame_layout_container_lending, lendingMasterFragment)
            }

        }
        lendingMasterFragment

        transaction.commit()
    }

    /**
     * Method that navigates to the lending objects overview fragment
     *
     * @return [Unit]
     **/
    fun navigateToMaster() {
        configureMasterDetailView(replace = true)
    }

    /**
     * Method that navigates to the add lending objects screen fragment
     *
     * @return [Unit]
     **/
    fun navigateToDetail() {
        if (!masterDetail) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout_container_lending, lendingDetailFragment)
                .commit()
        }
    }

    /**
     * Method that navigates to the add lending objects screen fragment
     *
     * @return [Unit]
     **/
    fun navigateToAdd() {
        val transaction = supportFragmentManager.beginTransaction()
        if (masterDetail) {
            frame_layout_details_lending!!.visibility = View.GONE
            transaction.replace(R.id.frame_layout_master_lending, addFragment)
        } else {
            transaction.replace(R.id.frame_layout_container_lending, addFragment)
        }

        transaction.commit()
    }

    /**
     * Method that updates the view of [lendingDetailFragment] when an object in the [lendingMasterFragment]
     * is clicked:
     *
     * Either by updating the [LendingDetailFragment.viewModel] property by posting the selected [LendingObject]
     * when [masterDetail]=true, or by
     * Navigating to the [LendingDetailFragment] when [masterDetail]=false
     *
     * @return [Unit]
     **/
    fun onDetailClick(lendObject: LendingObject) {
        lendingDetailFragment.viewModel.lendingObject.postValue(lendObject)
        if (!masterDetail) {
            navigateToDetail()
        }
    }
}