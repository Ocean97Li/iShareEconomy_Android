package com.ocean.ishareeconomy_android.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ocean.ishareeconomy_android.models.User

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
            firstname = it.firstname,
            lastname = it.lastname,
            username = it.username,
            address = it.address,
            rating = it.rating,
            distance = it.distance,
            lending = emptyList() // LendObjects need to fetched
        )
    }
}




