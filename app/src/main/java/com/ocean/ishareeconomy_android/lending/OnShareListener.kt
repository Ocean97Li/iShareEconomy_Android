package com.ocean.ishareeconomy_android.lending

/**
 * Part of *lending*
 *
 * Interface listener implemented by fragment,
 * and passed to the view model to be called after a share action was taken
 *
**/
interface OnShareListener {
    /**
     * Method that navigates to the [LendingMasterFragment]
     */
    fun navigateBackToMaster()
}