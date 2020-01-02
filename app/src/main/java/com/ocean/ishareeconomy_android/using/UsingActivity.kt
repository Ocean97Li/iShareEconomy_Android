package com.ocean.ishareeconomy_android.using

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.lending.LendingActivity
import com.ocean.ishareeconomy_android.models.LendingObject
import kotlinx.android.synthetic.main.activity_using.*

/**
 * Part of *using*.
 *
 * Activity responsible for showing the lendobjects currently used by the user
 * @property usingMasterFragment: [UsingMasterFragment], builds the using overview screen
 * @property usingDetailFragment: [UsingDetailFragment] builds the detail view screen
 * @property onNavigationItemSelectedListener: [BottomNavigationView.OnNavigationItemSelectedListener]
 * @property masterDetail: [Boolean], reflects whether the views should be drawn in master detail or not
 * provides the implementation of BottomNavigation
 */
class UsingActivity : AppCompatActivity() {

    private var usingMasterFragment = UsingMasterFragment()
    private var usingDetailFragment = UsingDetailFragment()
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_lending -> {
                    val intent = Intent(this, LendingActivity::class.java)
                    startActivity(
                        intent,
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
                    )
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

    val detail: LiveData<LendingObject> = MutableLiveData()

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
     * Method that navigates to the detail screen fragment
     *
     * @return [Unit]
     **/
    private fun navigateToDetail() {
        usingDetailFragment = UsingDetailFragment()
        if (!masterDetail) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout_container_using, usingDetailFragment)
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
     * Method that navigates to the detail view when an list object in the [usingDetailFragment]
     * is clicked. It sets the blank [UsingDetailFragment], which will be updated on initialisation by the repo
     *
     * @return [Unit]
     **/
    fun onDetailClick() {
        navigateToDetail()
    }
}
