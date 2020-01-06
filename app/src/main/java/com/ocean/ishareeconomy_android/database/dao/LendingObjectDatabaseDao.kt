package com.ocean.ishareeconomy_android.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ocean.ishareeconomy_android.database.entities.DatabaseLendObject
import com.ocean.ishareeconomy_android.database.relationships.LendObjectWithObjectUsers
import com.ocean.ishareeconomy_android.models.LendingObject

/**
 * Part of *database.dao*.
 *
 * Defines the possible sql actions, concerning the local storage of objects of type [LendingObject] in the DB
 */
@Dao
abstract class LendObjectDatabaseDao {

    @Transaction
    @Query("SELECT * from lendobjects where object_owner_id = :id")
    abstract fun getLendingObjectsFromUserById(id: String): LiveData<List<LendObjectWithObjectUsers>>

    @Transaction
    @Query("SELECT * from lendobjects where object_owner_id = :id")
    abstract fun lendingObjectsForUserById(id: String): List<LendObjectWithObjectUsers>

    @Transaction
    @Query(
        """
            SELECT OBU.object_id, object_owner_id, object_owner_name, name, description, type 
            from lendobjects LO left join object_users OBU on LO.object_id = OBU.object_id  
            where OBU.current and OBU.user_id = :id"""
    )
    abstract fun getObjectsCurrentlyUsedByUser(id: String): LiveData<List<LendObjectWithObjectUsers>>

    @Transaction
    @Query(
        """
            SELECT OBU.object_id, object_owner_id, object_owner_name, name, description, type 
            from lendobjects LO left join object_users OBU on LO.object_id = OBU.object_id  
            where OBU.current and OBU.user_id = :id"""
    )
    abstract fun usingObjectsForUserById(id: String): List<LendObjectWithObjectUsers>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllLendObjects(vararg objects: DatabaseLendObject)

    @Query("DELETE FROM lendobjects WHERE object_id = :id")
    abstract fun deleteObjectById(id: String)

    @Query("DELETE FROM lendobjects")
    abstract fun deleteFromObjects()

    @Transaction
    open fun putAllLendObjects(vararg objects: DatabaseLendObject) {
        deleteFromObjects()
        insertAllLendObjects(*objects)
    }

}