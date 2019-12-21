package com.ocean.ishareeconomy_android.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ocean.ishareeconomy_android.database.entities.DatabaseObjectUser

@Dao
interface ObjectUserDatabaseDao {

    @Transaction
    @Insert
    fun insertAllObjectUsers(vararg objectUsers: DatabaseObjectUser)

    @Query("DELETE FROM object_users")
    fun deleteFromObjectUser()
}