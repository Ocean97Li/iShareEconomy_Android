package com.ocean.ishareeconomy_android.models

import androidx.room.Ignore
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
 *  @property id the id of the object owner
 *  @property name the owner's full name
 *  @property fromDate the start of the lending period
 *  @property toDate the end of the lending period
 */
class ObjectUser(
    @Ignore
    var objectuserid: Int = 0,
    id: String,
    name: String,
    @SerializedName("fromdate")
    val from: Date,
    @SerializedName("todate")
    val to: Date,
    var parenObjectId: String = "") : ObjectOwner(id, name) {
}