package com.ocean.ishareeconomy_android.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ocean.ishareeconomy_android.models.ObjectUser
import java.sql.Date

@Entity(tableName = "object_users", foreignKeys = [
    ForeignKey(
        entity = DatabaseLendObject::class,
        parentColumns = arrayOf("object_id"),
        childColumns = arrayOf("object_id"),
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = DatabaseUser::class,
        parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("object_user_id"),
        onDelete = ForeignKey.CASCADE
    )
])
data class DatabaseObjectUser constructor(
    @PrimaryKey(autoGenerate = true)
    val object_user_id: Int,
    val object_id: String,
    val user_id: String,
    val user_name: String,
    val from_date: Date,
    val to_date: Date)

fun List<DatabaseObjectUser>.asDomainModel(): List<ObjectUser> {
    return map {
        ObjectUser(
            id = it.user_id,
            name = it.user_name,
            from = it.from_date,
            to = it.to_date
        )
    }
}
