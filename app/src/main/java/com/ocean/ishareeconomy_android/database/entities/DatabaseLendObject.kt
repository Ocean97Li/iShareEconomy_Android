package com.ocean.ishareeconomy_android.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.models.ObjectOwner

/**
 * Part of *database.entities*.
 *
 * Defines how domain objects of type [LendingObject] and [ObjectOwner] are stored in the DB
 * @property object_id The identifier for [DatabaseLendObject], matches [LendingObject.id]
 * @property object_owner_id matches the id of [LendingObject.owner]
 * @property object_owner_name matches the name of [LendingObject.owner]
 * @property name the object's name
 * @property description the object's description
 * @property type the object's type in [String]
 *
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




