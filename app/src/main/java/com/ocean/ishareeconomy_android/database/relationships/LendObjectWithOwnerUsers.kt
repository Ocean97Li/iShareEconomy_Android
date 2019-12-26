package com.ocean.ishareeconomy_android.database.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.ocean.ishareeconomy_android.database.entities.DatabaseLendObject
import com.ocean.ishareeconomy_android.database.entities.DatabaseObjectUser
import com.ocean.ishareeconomy_android.database.entities.asDomainModel
import com.ocean.ishareeconomy_android.models.*

data class LendObjectWithObjectUsers(
    @Embedded
    val lendobject: DatabaseLendObject,
    @Relation(
        parentColumn = "object_id",
        entityColumn = "object_id"
    )
    val users: List<DatabaseObjectUser> = emptyList()
)

fun List<LendObjectWithObjectUsers>.asDomainModel(): List<LendingObject> {
    return map {
        val user =  findUser(it.users)?.asDomainModel()
        LendingObject(
            id = it.lendobject.object_id,
            name = it.lendobject.name,
            description = it.lendobject.description,
            type = it.lendobject.type,
            owner = ObjectOwner(it.lendobject.object_owner_id, it.lendobject.object_owner_name),
            user = user,
            waitingList = it.users.asDomainModel().filter { objcuser -> objcuser.objectUserId != user?.objectUserId }
        )
    }
}

fun findUser(list: List<DatabaseObjectUser>): DatabaseObjectUser? {
    return list.find {  objectUser ->
        objectUser.current
    }
}