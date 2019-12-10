package com.ocean.ishareeconomy_android.models
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
    id: String,
    name: String,
    description: String,
    type: LendObjectType,
    owner: ObjectOwner,
    user: ObjectUser,
    waitingList: List<LendingObject>
)