package com.ocean.ishareeconomy_android.models

import com.google.gson.annotations.SerializedName
import com.ocean.ishareeconomy_android.models.LendObjectType.*
import java.io.Serializable

/**
 * Part of *models*.
 *
 * [LendingObject] is an [Tool], [Service] or [Transportation] that one [User] lends out and that other users can use
 * @property id the lendobject's id
 * @property name the name of the lendobject
 * @property description a short description text
 * @property type: [String], the type in string form
 * @property type: [LendObjectType], the type: [Tool], [Service] or [Transportation]
 * @property owner refers to the [User] that is the lendobject's owner
 * @property user refers to [User] that is the lendobject's current user
 * @property waitingList a [List]<[User]> that is the lendobject's future users
 *
 * @constructor creates a [LendingObject] that extend [Serializable] for onSafeInstance
 * @param id the [LendingObject]'s id
 * @param name the name of the [LendingObject]
 * @param description a short description text
 * @param type a representing [String] the [LendObjectType]
 * @param owner refers to the [User] that is the [LendingObject]'s owner
 * @param user refers to [User] that is the [LendingObject]'s current user
 * @param waitingList a [List]<[User]> that is the [LendingObject]'s future users
 */
class LendingObject(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val description: String,
    var type: String,
    val owner: ObjectOwner,
    var user: ObjectUser?,
    @SerializedName("waitinglist")
    var waitingList: List<ObjectUser> = emptyList()
) : Serializable {
    val lendObjectType = LendObjectType.fromString(type)
}
