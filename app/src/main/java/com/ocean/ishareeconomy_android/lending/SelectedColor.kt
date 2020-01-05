package com.ocean.ishareeconomy_android.lending

import com.ocean.ishareeconomy_android.models.LendObjectType

/**
 * Part of *lending*
 *
 * Interface listener implemented by fragment,
 * and passed to the view model to be called after a button was clicked
 *
 *
 *
 **/
interface SelectedColor {
    fun setSelected(type: LendObjectType)
}