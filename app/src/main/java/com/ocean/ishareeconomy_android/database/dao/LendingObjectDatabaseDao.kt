package com.ocean.ishareeconomy_android.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ocean.ishareeconomy_android.database.entities.DatabaseLendObject
import com.ocean.ishareeconomy_android.database.relationships.LendObjectWithObjectUsers

@Dao
interface LendObjectDatabaseDao {

    @Transaction
    @Query("SELECT * from lendobjects where object_owner_id = :id")
    fun getLendingObjectsFromUserById(id: String): LiveData<List<LendObjectWithObjectUsers>>

    @Transaction
    @Query("SELECT * from lendobjects where object_owner_id = :id")
    fun lendingObjectsForUserById(id: String): List<LendObjectWithObjectUsers>


    // I may need to write a query here to fetch the lendobjects that are asociated with the passed user id,
    // filtered on the current period (from date <= current date) signalling that the lending period has started
    // Since the lend objects table should be cleared when new information
    @Transaction
    @Query(
        """
            SELECT OBU.object_id, object_owner_id, object_owner_name, name, description, type 
            from lendobjects LO left join object_users OBU on LO.object_id = OBU.object_id  
            where OBU.current and OBU.user_id = :id"""
    )
    fun getObjectsCurrentlyUsedByUser(id: String): LiveData<List<LendObjectWithObjectUsers>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLendObjects(vararg objects: DatabaseLendObject)

}