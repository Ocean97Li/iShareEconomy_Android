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

package com.ocean.ishareeconomy_android.network

import com.ocean.ishareeconomy_android.database.entities.DatabaseLendObject
import com.ocean.ishareeconomy_android.database.entities.DatabaseObjectUser
import com.ocean.ishareeconomy_android.database.entities.DatabaseUser
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.models.ObjectUser
import com.ocean.ishareeconomy_android.models.User
import java.sql.Date

/**
 * Convert Domain objects into to database objects
 */

fun List<User>.asDatabaseModel(): Array<DatabaseUser> {
    return map {
        DatabaseUser (
                user_id = it.id,
                firstname = it.firstname,
                lastname = it.lastname,
                username = it.username,
                address = it.address,
                rating = it.rating,
                distance = it.distance
        )
    }.toTypedArray()
}

fun List<LendingObject>.asDatabaseModel(): Array<DatabaseLendObject> {
    return map {
        DatabaseLendObject (
            object_id = it.id,
            name = it.name,
            description = it.description,
            type = it.type.toString(),
            object_owner_id = it.owner.id,
            object_owner_name = it.owner.name
        )
    }.toTypedArray()
}

fun List<ObjectUser>.asDatabaseModels(): Array<DatabaseObjectUser> {
    return map {
        DatabaseObjectUser (
            object_user_id = 0, // hope this will get autogenerated
            object_id = "", // this needs to be set manually
            user_id = it.id,
            user_name = it.name,
            from_date = Date(it.from.time),
            to_date = Date(it.to.time)
        )
    }.toTypedArray()
}