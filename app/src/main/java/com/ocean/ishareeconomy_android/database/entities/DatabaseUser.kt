package com.ocean.ishareeconomy_android.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ocean.ishareeconomy_android.models.User

/**
 * Part of *database.entities*.
 *
 * Defines how the domain objects of type [User] are stored in the DB
 * @property user_id the identifier for the user, matches [User.id]
 * @property firstname the user's firstname
 * @property lastIndex the user's lastname
 * @property username the user's username
 * @property address the user's full address
 * @property rating the automatically determined rating the user has
 * @property distance the distance of this user from the logged in user in km
 */
@Entity(tableName = "users")
data class DatabaseUser constructor(
    @PrimaryKey
    val user_id: String,
    val firstname: String,
    val lastname: String,
    val username: String,
    val address: String,
    val rating: Int,
    val distance: Double
)

fun List<DatabaseUser>.asDomainModel(): List<User> {
    return map {
        User(
            id = it.user_id,
            firstName = it.firstname,
            lastName = it.lastname,
            username = it.username,
            address = it.address,
            rating = it.rating,
            distance = it.distance,
            lending = emptyList(),
            using = emptyList()
        )
    }
}




