package com.ocean.ishareeconomy_android.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ocean.ishareeconomy_android.database.entities.DatabaseObjectUser
import com.ocean.ishareeconomy_android.models.ObjectUser

/**
 * Part of *database.dao*.
 *
 * Defines the possible sql actions, concerning the local storage of objects of type [ObjectUser] in the DB
 */
@Dao
interface ObjectUserDatabaseDao {

    @Transaction
    @Insert
    fun insertAllObjectUsers(vararg objectUsers: DatabaseObjectUser)

    @Query("DELETE FROM object_users")
    fun deleteFromObjectUser()
}