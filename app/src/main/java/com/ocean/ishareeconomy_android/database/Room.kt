/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ocean.ishareeconomy_android.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ocean.ishareeconomy_android.database.converters.DateConverter
import com.ocean.ishareeconomy_android.database.dao.LendObjectDatabaseDao
import com.ocean.ishareeconomy_android.database.dao.ObjectUserDatabaseDao
import com.ocean.ishareeconomy_android.database.dao.UserDatabaseDao
import com.ocean.ishareeconomy_android.database.entities.DatabaseLendObject
import com.ocean.ishareeconomy_android.database.entities.DatabaseObjectUser
import com.ocean.ishareeconomy_android.database.entities.DatabaseUser

/**
 * Part of *database*.
 *
 * Main [RoomDatabase] database singleton access point for the app,
 * provides local SQL storage
 *
 * @property users the DAO that stores the queries for users
 * @property objects the DAO that stores object related queries
 * @property objectUsers the DAO that stores objectUser related queries
 */
@Database(
    entities = [DatabaseUser::class, DatabaseLendObject::class, DatabaseObjectUser::class],
    exportSchema = false,
    version = 1)
@TypeConverters(DateConverter::class)
abstract class IShareDataBase : RoomDatabase() {
    abstract val users: UserDatabaseDao
    abstract val objects: LendObjectDatabaseDao
    abstract  val objectUsers: ObjectUserDatabaseDao
}

// singleton
private lateinit var INSTANCE: IShareDataBase

fun getDatabase(context: Context): IShareDataBase {
    synchronized(IShareDataBase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                IShareDataBase::class.java,
                    "database").build()
        }
    }

    return INSTANCE
}
