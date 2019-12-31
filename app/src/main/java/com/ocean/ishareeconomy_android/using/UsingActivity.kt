package com.ocean.ishareeconomy_android.using

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.lending.LendingActivity
import com.ocean.ishareeconomy_android.lending.LendingDetailFragment
import com.ocean.ishareeconomy_android.models.LendingObject
import kotlinx.android.synthetic.main.activity_lending.*
import kotlinx.android.synthetic.main.activity_lending.nav_view_lending
import kotlinx.android.synthetic.main.activity_using.*
import kotlinx.android.synthetic.main.fragment_add_lending.*

/**
 * Part of *using*.
 *
 * Activity responsible for showing the lendobjects currently used by the user
 * @property usingMasterFragment builds the using overview screen
 * @property usingDetailFragment builds the detail view screen
 * @property onNavigationItemSelectedListener: [BottomNavigationView.OnNavigationItemSelectedListener]
 * provides the implementation of BottomNavigation
 */
class UsingActivity : AppCompatActivity() {

    private var usingMasterFragment =  UsingMasterFragment()
    private var usingDetailFragment =  UsingDetailFragment()
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_lending -> {
                val intent = Intent(this, LendingActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                this.finish()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_using -> {
                navigateToMaster()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_requests -> {
//                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    // Master Detail View Switch
    var masterDetail = false

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
        nav_view_using.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (master_detail_using != null) {
            masterDetail = true
        }

        if (savedInstanceState == null) {
            configureMasterDetailView()
        }

        window.enterTransition = null
        window.exitTransition = null

        nav_view_using.menu.getItem(1).isChecked = true
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
                .replace(usingMasterFragment.id, usingDetailFragment)
                .commit()
        }
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
                transaction.replace(usingMasterFragment.id, usingMasterFragment)
                frame_layout_details_using!!.visibility = View.VISIBLE
            } else {
                transaction.add(R.id.frame_layout_master_using, usingMasterFragment)
                transaction.add(R.id.frame_layout_details_using, usingDetailFragment)
            }

        } else {

            if (replace) {
                transaction.replace(R.id.frame_layout_container_using, usingMasterFragment)
            } else {
                transaction.add(R.id.frame_layout_container_using, usingMasterFragment)
            }

        }

        transaction.commit()
    }

    /**
     * Method that updates the view of [usingDetailFragment] when an object in the [usingDetailFragment]
     * is clicked:
     *
     * Either by updating the [UsingDetailFragment.viewModel] property by posting the selected [LendingObject]
     * when [masterDetail]=true, or by
     * Navigating to the [UsingDetailFragment] when [masterDetail]=false
     *
     * @return [Unit]
     **/
    fun onDetailClick(lendObject: LendingObject) {
        usingDetailFragment.viewModel.lendingObject.postValue(lendObject)
        if (!masterDetail) {
            navigateToDetail()
        }
    }
}
