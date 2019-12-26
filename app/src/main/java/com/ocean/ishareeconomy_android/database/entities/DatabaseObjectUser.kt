package com.ocean.ishareeconomy_android.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ocean.ishareeconomy_android.models.ObjectUser
import java.sql.Date

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
