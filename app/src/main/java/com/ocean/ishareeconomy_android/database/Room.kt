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
import androidx.room.*
import com.ocean.ishareeconomy_android.database.converters.DateConverter
import com.ocean.ishareeconomy_android.database.dao.LendObjectDatabaseDao
import com.ocean.ishareeconomy_android.database.dao.ObjectUserDatabaseDao
import com.ocean.ishareeconomy_android.database.dao.UserDatabaseDao
import com.ocean.ishareeconomy_android.database.entities.DatabaseLendObject
import com.ocean.ishareeconomy_android.database.entities.DatabaseObjectUser
import com.ocean.ishareeconomy_android.database.entities.DatabaseUser

@Database(
    entities = [DatabaseUser::class, DatabaseLendObject::class, DatabaseObjectUser::class],
    exportSchema = false,
    version = 1)
@TypeConverters(DateConverter::class)
abstract class iShareDataBase : RoomDatabase() {
    abstract val users: UserDatabaseDao
    abstract val objects: LendObjectDatabaseDao
    abstract  val objectUsers: ObjectUserDatabaseDao
}

// singleton
private lateinit var INSTANCE: iShareDataBase

fun getDatabase(context: Context): iShareDataBase {
    synchronized(iShareDataBase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                iShareDataBase::class.java,
                    "database").build()
        }
    }

    return INSTANCE
}
