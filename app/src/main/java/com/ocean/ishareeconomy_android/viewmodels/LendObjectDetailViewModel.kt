package com.ocean.ishareeconomy_android.viewmodels

import android.graphics.drawable.ColorDrawable
import com.ocean.ishareeconomy_android.models.ObjectUser
import com.ocean.ishareeconomy_android.models.ObjectOwner
import com.ocean.ishareeconomy_android.models.LendingObject

/**
 * Part of *viewmodels*.
 *
 * The viewmodel that is used to display the detail view of individual items of type [LendingObject],
 * it's a simple abstraction that extends [LendObjectViewModel] to hold the [LendingObject] and
 * the icon background color [ColorDrawable], the latter is determined by the viewAdapter and also
 * responsible for determining the object's icon.
 * Furthermore it holds the [owner]:[ObjectOwner] and [user]:[ObjectUser].
 * finally, it also holds the [waitingList]:[List]<[ObjectUser]>
 *
 * @property data: [LendingObject] the object, used to draw the object card
 * @property color: [ColorDrawable] the object's icon background color, indicating the usage
 * @property owner: [String] the owner's full name, used to draw the owner card
 * @property user: [String] the user's full name, used to draw the user card
 * @property waitingList: [ObjectUser] the list of waiting users
 */
class LendObjectDetailViewModel(data: LendingObject, color: ColorDrawable): LendObjectViewModel(
   data, color) {
    val owner = data.owner.name
    val user = data.user?.name
    val waitingList = data.waitingList
}
