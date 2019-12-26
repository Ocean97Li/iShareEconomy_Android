package com.ocean.ishareeconomy_android

import com.ocean.ishareeconomy_android.models.ObjectOwner
import com.ocean.ishareeconomy_android.models.ObjectUser
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class ObjectUserOwnerTest {
    private lateinit var owner: ObjectOwner
    private lateinit var user: ObjectUser
    private lateinit var fromDate: Date
    private lateinit var toDate: Date

    @Before
    fun init(){
        fromDate = Date()
        toDate = Date()
        owner = ObjectOwner("foo bar id", "Foo Bar")
        user = ObjectUser(0, "foo bar id", "Foo Bar", fromDate, toDate)
    }

    @Test
    fun objectOwnerInitializedCorrectly() {
        assertEquals("foo bar id", owner.id)
        assertEquals("Foo Bar", owner.name)
    }

    @Test
    fun userOwnerInitializedCorrectly() {
        assertEquals("foo bar id", owner.id)
        assertEquals("Foo Bar", owner.name)
    }
}