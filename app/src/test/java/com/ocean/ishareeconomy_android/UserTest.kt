package com.ocean.ishareeconomy_android

import com.ocean.ishareeconomy_android.models.User
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class UserTest {
    private lateinit var user: User
    @Before
    fun init() {
        user = User(
            id = "id",
            firstName = "foo",
            lastName = "bar",
            address = "home",
            username = "foobar",
            rating = 10,
            distance = 0.0,
            lending = emptyList(),
            using = emptyList()
        )
    }

    @Test
    fun userInitializedCorrectlyTest() {
        assertEquals("id",user.id)
        assertEquals("foo",user.firstName)
        assertEquals("bar",user.lastName)
        assertEquals("home",user.address)
        assertEquals("foobar",user.username)
        assertEquals(10,user.rating)
        assertEquals(0.0,user.distance, 0.0)
        assertTrue(user.lending.isEmpty())
        assertTrue(user.using.isEmpty())
    }

    @Test
    fun userFullNameReturnsCorrectly(){
        assertEquals("Foo Bar", user.fullName)
    }
}