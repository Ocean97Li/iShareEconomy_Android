package com.ocean.ishareeconomy_android.login.login

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
open class LoginActivityTest {
    @Rule
    @JvmField
    public var loginTestRule: LoginTestRule =
        LoginTestRule()

    @Test
    fun contextTest() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.ocean.ishareeconomy_android", context.packageName)
    }
}