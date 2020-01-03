package com.ocean.ishareeconomy_android.models

import com.google.gson.annotations.SerializedName
import java.util.*


/**
 * Part of *models*.
 *
 * User object that is used to login and then fetch the correct user object
 * @property id the id of the object owner
 * @property name the owner's full name
 */
open class ObjectOwner(
    val id: String,
    val name: String
)

/**
 * Part of *models*.
 *
 * User object that is used to login and then fetch the correct user object
 *  @property objectUserId: [Int] the generated unique identifier for a [ObjectUser]
 *  @property id: [String] the id of the object owner
 *  @property name: [String] the owner's full name
 *  @property from: [Date] the starting date of the lending period
 *  @property to: [Date] the end date of the lending period
 *  @property parenObjectId: [String] refers to the parent [LendingObject], so that it may be fetched
 */
class ObjectUser(
    var objectUserId: Int = 0,
    id: String,
    name: String,
    @SerializedName("fromdate")
    val from: Date,
    @SerializedName("todate")
    val to: Date,
    var parenObjectId: String = ""
) : ObjectOwner(id, name)
