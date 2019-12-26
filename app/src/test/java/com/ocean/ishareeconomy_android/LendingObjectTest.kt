package com.ocean.ishareeconomy_android

import com.ocean.ishareeconomy_android.models.LendObjectType
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.models.ObjectOwner
import com.ocean.ishareeconomy_android.models.ObjectUser
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException
import java.util.*

class LendingObjectTest {

    private lateinit var lendObject1: LendingObject
    private lateinit var lendObject2: LendingObject
    private lateinit var lendObject3: LendingObject

    @Before
    fun init() {
        lendObject1 = LendingObject(
            id = "id",
            name = "foo",
            description = "bar",
            type = "tool",
            waitingList = emptyList(),
            owner = ObjectOwner("foo id","Foo Bar"),
            user = null
        )

        lendObject2 = LendingObject(
            id = "id",
            name = "foo",
            description = "bar",
            type = "service",
            waitingList = listOf(ObjectUser(0,"foo id", "Foo Bar", Date(), Date())),
            owner = ObjectOwner("foo id","Foo Bar"),
            user = null
        )

        lendObject3 = LendingObject(
            id = "id",
            name = "foo",
            description = "bar",
            type = "transportation",
            waitingList = emptyList(),
            owner = ObjectOwner("foo id","Foo Bar"),
            user = ObjectUser(0,"foo id", "Foo Bar", Date(), Date())
        )
    }

    @Test
    fun lendObjectDefaultInitializedCorrectly() {
        // Default object
        assertEquals("id",lendObject1.id)
        assertEquals("foo",lendObject1.name)
        assertEquals("bar",lendObject1.description)
        assertTrue(lendObject1.waitingList.isEmpty())
        assertNull(lendObject1.user)
    }

    @Test
    fun lendObjectWithWaitingListInitializedCorrectly() {
        // Object with waitingList
        assertEquals("id",lendObject2.id)
        assertEquals("foo",lendObject2.name)
        assertEquals("bar",lendObject2.description)
        assertTrue(lendObject2.waitingList.isNotEmpty())
        assertNull(lendObject2.user)
    }

    @Test
    fun lendObjectWithUserInitializedCorrectly() {
        // Object with user
        assertEquals("id",lendObject3.id)
        assertEquals("foo",lendObject3.name)
        assertEquals("bar",lendObject3.description)
        assertTrue(lendObject3.waitingList.isEmpty())
        assertNotNull(lendObject3.user)
    }

    @Test
    fun lendObjectsTypeInitializedCorrectly() {
        // Object with user
        assertEquals(LendObjectType.Tool,lendObject1.lendObjectType)
        assertEquals(LendObjectType.Service,lendObject2.lendObjectType)
        assertEquals(LendObjectType.Transportation,lendObject3.lendObjectType)
    }

    @Test(expected = IllegalArgumentException::class)
    fun lendObjectTypeThrowsErrorInLendingObjectCorrectly() {
        val invalidType = "item"
        LendingObject(
            lendObject1.id,
            lendObject1.name,
            lendObject1.description,
            invalidType,
            lendObject1.owner,
            lendObject1.user,
            lendObject1.waitingList
        )
    }
}