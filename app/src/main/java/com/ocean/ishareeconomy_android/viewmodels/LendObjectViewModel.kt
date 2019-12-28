package com.ocean.ishareeconomy_android.viewmodels

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.models.LendObjectType
import com.ocean.ishareeconomy_android.models.LendingObject

/**
 * Part of *viewmodels*.
 *
 * The viewmodel that is used to display individual items of type [LendingObject], it's a simple
 * abstraction that holds the [LendingObject] and the icon background color [ColorDrawable],
 * the latter is determined by the viewAdapter. Also responsible for determining the object's icon
 *
 * @property data: [LendingObject] the object
 * @property color: [ColorDrawable] the object's icon background color, indicating the usage
 *
 */
open class LendObjectViewModel(
    private val data: LendingObject, val color: ColorDrawable
) {
    val name = data.name
    val description = data.description
    val numberOfUsers = (data.waitingList.size + if (data.user != null) 1 else 0).toString()

    /**
     * Returns the icon that correspond to the [LendObjectType] of [data]
     *
     * @return An R.drawable.id [Int]
     */
    fun type() : Int {
        return when(data.lendObjectType) {
            LendObjectType.Tool -> R.drawable.ic_lendobject_tool_solid
            LendObjectType.Service -> R.drawable.ic_lendobject_service_solid
            LendObjectType.Transportation -> R.drawable.ic_lendobject_transportation_solid
        }
    }
}