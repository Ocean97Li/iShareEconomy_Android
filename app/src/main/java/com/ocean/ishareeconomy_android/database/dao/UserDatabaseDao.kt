package com.ocean.ishareeconomy_android.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ocean.ishareeconomy_android.database.entities.DatabaseUser
import com.ocean.ishareeconomy_android.models.User

/**
 * Part of *database.dao*.
 *
 * Defines the possible sql actions, concerning the local storage of objects of type [User] in the DB
 */
@Dao
abstract class UserDatabaseDao {
    @Query("SELECT * from users")
    abstract fun getAllUsers(): LiveData<List<DatabaseUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllUsers(vararg users: DatabaseUser)

    @Query("SELECT distance from users where user_id = :id")
    abstract fun getDistanceFromLoggedInUser(id: String): Int

    @Query("DELETE FROM users")
    abstract fun deleteFromUsers()

    @Transaction
    open fun putAllUsers(vararg users: DatabaseUser) {
        deleteFromUsers()
        insertAllUsers(*users)
    }
}