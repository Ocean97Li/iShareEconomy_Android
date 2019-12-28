package com.ocean.ishareeconomy_android.database.relationships

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.RoomDatabase
import com.ocean.ishareeconomy_android.database.entities.DatabaseUser
import com.ocean.ishareeconomy_android.models.User

/**
 * Part of *database.relationships*.
 *
 * Defines the relationship between [DatabaseUser] and [LendObjectWithObjectUsers]
 * Only used to fetch, not to store
 */
data class UserWithLendObjects(
    @Embedded
    val user: DatabaseUser,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "object_owner_id"
    )
    val lending: List<LendObjectWithObjectUsers>

)

fun List<UserWithLendObjects>.asDomainModel(): List<User> {
    return map {
        User(
            id = it.user.user_id,
            username = it.user.username,
            firstName = it.user.firstname,
            lastName = it.user.lastname,
            address = it.user.address,
            rating = it.user.rating,
            distance = it.user.distance,
            lending = it.lending.asDomainModel()
        )
    }
}