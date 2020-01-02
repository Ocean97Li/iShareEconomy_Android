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
 *  @property id the [String] id of the object owner
 *  @property name the owner's full name [String]
 *  @property from the starting [Date] of the lending period
 *  @property to the end [Date] of the lending period
 */
class ObjectUser(
    var objectUserId: Int = 0,
    id: String,
    name: String,
    @SerializedName("fromdate")
    val from: Date,
    @SerializedName("todate")
    val to: Date,
    var parenObjectId: String = "",
    var distance: Double = 0.0
) : ObjectOwner(id, name)
