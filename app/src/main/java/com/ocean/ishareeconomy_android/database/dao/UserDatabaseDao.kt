package com.ocean.ishareeconomy_android.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ocean.ishareeconomy_android.database.entities.DatabaseUser
import com.ocean.ishareeconomy_android.models.User

/**
 * Part of *database.dao*.
 *
 * Defines the actions, concerning the local storage of objects of type [User] in the DB
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