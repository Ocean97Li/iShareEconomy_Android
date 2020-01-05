package com.ocean.ishareeconomy_android.viewmodels

import com.ocean.ishareeconomy_android.models.ObjectOwner
/**
 * Part of *viewmodels*.
 *
 * The viewmodel used to draw a card representing the [ObjectOwner] of a lendobject
 *
 * @property name the name is the only thing we need to display from the [ObjectOwner]
 *
 * @param owner an object of the type [ObjectOwner]
 */
class OwnerObjectViewModel(owner: ObjectOwner) {
    val name = owner.name
}