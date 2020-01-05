package com.ocean.ishareeconomy_android.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ocean.ishareeconomy_android.models.ObjectUser
import java.sql.Date

/**
 * Part of *database.entities*.
 *
 * Defines how the domain objects of type [ObjectUser] are stored in the DB
 * @property object_user_id the generated identifier for the concerning user object combination
 * @property object_id the object's id, matching that of a [DatabaseLendObject]
 * @property user_id the user's id, matching that of a [DatabaseObjectUser]
 * @property user_name the user's full name
 * @property from_date the date starting from which the user can use the object
 * @property to_date the date on which the object needs to be returned
 * @property current An extra option to distinguish objects that are currently in use from the others
 */
@Entity(tableName = "object_users")
data class DatabaseObjectUser constructor(
    @PrimaryKey(autoGenerate = true)
    val object_user_id: Int,
    val object_id: String,
    val user_id: String,
    val user_name: String,
    val from_date: Date,
    val to_date: Date,
    var current: Boolean)

fun List<DatabaseObjectUser>.asDomainModel(): List<ObjectUser> {
    return map {
        ObjectUser(
            objectUserId = it.object_user_id,
            id = it.user_id,
            name = it.user_name,
            from = it.from_date,
            to = it.to_date
        )
    }
}

fun DatabaseObjectUser.asDomainModel(): ObjectUser {
    return ObjectUser(
            objectUserId = this.object_user_id,
            id = this.user_id,
            name = this.user_name,
            from = this.from_date,
            to = this.to_date
    )
}
