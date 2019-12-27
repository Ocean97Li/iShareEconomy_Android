package com.ocean.ishareeconomy_android.database.entities

import androidx.room.*
import com.ocean.ishareeconomy_android.models.*

/**
 * Part of *database.entities*.
 *
 * Defines how objects of type [LendingObject] and [ObjectOwner] are stored in the DB
 */
@Entity(tableName = "lendobjects", foreignKeys = [
    ForeignKey(
        entity = DatabaseUser::class,
        parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("object_owner_id"),
        onDelete = ForeignKey.CASCADE
    )
], indices = [Index(value = ["object_owner_id", "object_id"])])
data class DatabaseLendObject constructor(
    @PrimaryKey
    val object_id: String,
    val object_owner_id: String,
    val object_owner_name: String,
    val name: String,
    val description: String,
    val type: String
)




