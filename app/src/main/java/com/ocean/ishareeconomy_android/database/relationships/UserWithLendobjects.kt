package com.ocean.ishareeconomy_android.database.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.ocean.ishareeconomy_android.database.entities.DatabaseUser
import com.ocean.ishareeconomy_android.models.User

data class UserWithLendobjects(
    @Embedded
    val user: DatabaseUser,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "object_owner_id"
    )
    val lending: List<LendObjectWithObjectUsers>

)

fun List<UserWithLendobjects>.asDomainModel(): List<User> {
    return map {
        User(
            id = it.user.user_id,
            username = it.user.username,
            firstname = it.user.firstname,
            lastname = it.user.lastname,
            address = it.user.address,
            rating = it.user.rating,
            distance = it.user.distance,
            lending = it.lending.asDomainModel()
        )
    }
}