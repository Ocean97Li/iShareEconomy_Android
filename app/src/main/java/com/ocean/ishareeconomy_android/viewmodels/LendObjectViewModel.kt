package com.ocean.ishareeconomy_android.viewmodels

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.models.LendObjectType
import com.ocean.ishareeconomy_android.models.LendingObject


class LendObjectViewModel(
    private val data: LendingObject, val color: ColorDrawable
) {
    val name = data.name
    val description = data.description
    val numberOfUsers = Integer.toString(data.waitingList.size + if (data.user != null) 1 else 0)

    fun type() : Int {
        when(data.lendObjectType) {
            LendObjectType.Tool -> return R.drawable.ic_lendobject_tool_solid
            LendObjectType.Service -> return R.drawable.ic_lendobject_service_solid
            LendObjectType.Transportation -> return R.drawable.ic_lendobject_transportation_solid
        }
    }
}