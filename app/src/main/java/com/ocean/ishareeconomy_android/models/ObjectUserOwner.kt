package com.ocean.ishareeconomy_android.models

import java.util.*


/**
 * Part of *models*.
 *
 * User object that is used to login and then fetch the correct user object
 * @property id the id of the object owner
 * @property name the owner's full name
 */
open class ObjectOwner(
    id: String,
    name: String
)

/**
 * Part of *models*.
 *
 * User object that is used to login and then fetch the correct user object
 *  @property id the id of the object owner
 *  @property name the owner's full name
 *  @property fromDate the start of the lending period
 *  @property toDate the end of the lending period
 */
class ObjectUser(id: String, name: String, from: Date, to: Date) : ObjectOwner(id, name) {
}