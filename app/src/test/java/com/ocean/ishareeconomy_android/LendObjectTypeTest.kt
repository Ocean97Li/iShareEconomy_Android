package com.ocean.ishareeconomy_android

import com.ocean.ishareeconomy_android.models.LendObjectType
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException


class LendObjectTypeTest {

    private val tool = "tool"
    private val service = "service"
    private val transportation = "transportation"

    @Rule
    @JvmField
    var exceptionRule: ExpectedException = ExpectedException.none()

    @Test
    fun lendObjectsTypeInitializedCorrectly() {
        // Object with user
        Assert.assertEquals(LendObjectType.Tool, LendObjectType.fromString(tool))
        Assert.assertEquals(LendObjectType.Service, LendObjectType.fromString(service))
        Assert.assertEquals(LendObjectType.Transportation, LendObjectType.fromString(transportation))
    }

    @Test
    fun lendObjectTypeThrowsErrorCorrectly() {
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage("")
        val invalidType = "item"
        LendObjectType.fromString(invalidType)
    }
}