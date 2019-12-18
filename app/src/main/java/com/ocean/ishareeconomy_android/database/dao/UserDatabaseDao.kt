package com.ocean.ishareeconomy_android.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ocean.ishareeconomy_android.database.entities.DatabaseUser
import com.ocean.ishareeconomy_android.database.relationships.UserWithLendobjects

@Dao
interface UserDatabaseDao {
    @Query("SELECT * from users")
    fun getAllUsers(): LiveData<List<DatabaseUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(vararg users: DatabaseUser)
}