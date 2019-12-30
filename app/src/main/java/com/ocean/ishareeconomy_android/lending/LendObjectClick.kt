package com.ocean.ishareeconomy_android.lending

import com.ocean.ishareeconomy_android.models.LendingObject

/**
 * Click listener for [LendingObject]
 *
 * @property block: ([LendingObject]) -> [Unit])
 * anonymous block of code that takes a [LendingObject] parameter and returns [Unit]
 */
class LendObjectClick(val block: (LendingObject) -> Unit) {
    /**
     * Called when a video is clicked
     *
     * @param lendObject the video that was clicked
     */
    fun onClick(lendObject: LendingObject) = block(lendObject)
}