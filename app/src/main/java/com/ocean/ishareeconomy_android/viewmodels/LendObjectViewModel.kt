package com.ocean.ishareeconomy_android.viewmodels

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
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
open class LendObjectViewModel (private val data: LendingObject, val context: Context) {

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

    /**
     * Method that returns a background color for the lendobject's type icon,
     * depending on it's state:
     *
     * Green for available, Yellow if there is a current user.
     *
     * @return a [ColorDrawable] color.
     */
    fun color(): ColorDrawable {
        return if (data.user != null) {
            ColorDrawable(ContextCompat.getColor(context, R.color.customYellow))
        } else {
            ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent))
        }
    }

    fun getLendObject(): LendingObject {
        return data
    }
}