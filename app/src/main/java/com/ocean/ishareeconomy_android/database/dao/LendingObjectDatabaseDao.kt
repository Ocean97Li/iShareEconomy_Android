package com.ocean.ishareeconomy_android.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ocean.ishareeconomy_android.database.entities.DatabaseLendObject
import com.ocean.ishareeconomy_android.database.relationships.LendObjectWithObjectUsers
import com.ocean.ishareeconomy_android.models.LendingObject

/**
 * Part of *database.dao*.
 *
 * Defines the actions, concerning the local storage of objects of type [LendingObject] in the DB
 */
@Dao
interface LendObjectDatabaseDao {

    @Transaction
    @Query("SELECT * from lendobjects where object_owner_id = :id")
    fun getLendingObjectsFromUserById(id: String): LiveData<List<LendObjectWithObjectUsers>>

    @Transaction
    @Query("SELECT * from lendobjects where object_owner_id = :id")
    fun lendingObjectsForUserById(id: String): List<LendObjectWithObjectUsers>

    @Transaction
    @Query(
        """
            SELECT OBU.object_id, object_owner_id, object_owner_name, name, description, type 
            from lendobjects LO left join object_users OBU on LO.object_id = OBU.object_id  
            where OBU.current and OBU.user_id = :id"""
    )
    fun getObjectsCurrentlyUsedByUser(id: String): LiveData<List<LendObjectWithObjectUsers>>

    @Transaction
    @Query(
        """
            SELECT OBU.object_id, object_owner_id, object_owner_name, name, description, type 
            from lendobjects LO left join object_users OBU on LO.object_id = OBU.object_id  
            where OBU.current and OBU.user_id = :id"""
    )
    fun usingObjectsForUserById(id: String): List<LendObjectWithObjectUsers>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLendObjects(vararg objects: DatabaseLendObject)

    @Query("DELETE FROM lendobjects WHERE object_id = :id")
    fun deleteObjectById(id: String)


}