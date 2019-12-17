package com.ocean.ishareeconomy_android.models

import com.google.gson.annotations.SerializedName

/**
 * Part of *models*.
 *
 * LendObject is an object, serive or transportation mode that one user lends out and that other users can use
 * @property id the lendobject's id
 * @property name the name of the lendobject
 * @property description a short description text
 * @property type the type: Object, Service or Transportation
 * @property owner refers to the User that is the lendobject's owner
 * @property user refers to User that is the lendobject's current user
 * @property waitingList a list of Users that is the lendobject's future users
 */
class LendingObject(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val description: String,
    var type: String,
    val owner: ObjectOwner,
    var user: ObjectUser?,
    var waitingList: List<ObjectUser>
) {
    val lendObjectTypepe = LendObjectType.fromString(type)
}