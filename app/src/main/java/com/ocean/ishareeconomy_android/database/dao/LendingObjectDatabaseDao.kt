package com.ocean.ishareeconomy_android.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ocean.ishareeconomy_android.database.entities.DatabaseLendObject
import com.ocean.ishareeconomy_android.database.entities.DatabaseUser
import com.ocean.ishareeconomy_android.database.relationships.LendObjectWithObjectUsers


@Dao
interface LendObjectDatabaseDao {

    @Transaction
    @Query("SELECT * from lendobjects where object_owner_id = :id")
    fun getLendingObjectsFromUserById(id: String): LiveData<List<LendObjectWithObjectUsers>>

    @Transaction
    @Query("SELECT * from lendobjects where object_owner_id = :id")
    fun lendingObjectsForUserById(id: String): List<LendObjectWithObjectUsers>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLendObjects(vararg objects: DatabaseLendObject)

}