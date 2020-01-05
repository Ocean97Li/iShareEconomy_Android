package com.ocean.ishareeconomy_android.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ocean.ishareeconomy_android.database.entities.DatabaseUser
import com.ocean.ishareeconomy_android.models.User

/**
 * Part of *database.dao*.
 *
 * Defines the possible sql actions, concerning the local storage of objects of type [User] in the DB
 */
@Dao
interface UserDatabaseDao {
    @Query("SELECT * from users")
    fun getAllUsers(): LiveData<List<DatabaseUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(vararg users: DatabaseUser)

    @Query("SELECT distance from users where user_id = :id")
    fun getDistanceFromLoggedInUser(id: String): Int
}