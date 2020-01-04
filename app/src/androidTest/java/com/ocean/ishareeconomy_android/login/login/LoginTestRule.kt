package com.ocean.ishareeconomy_android.login.login

import android.content.Context
import android.content.SharedPreferences
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.login.LoginActivity
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


class LoginTestRule : TestRule {

    @JvmField
    var restoreUsername: String? = null
    @JvmField
    var restorePassword: String? = null
    @JvmField
    var restoreToken: String? = null

    override fun apply(
        base: Statement,
        description: Description?
    ): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                setUp()
                val activityTestRule = ActivityTestRule(LoginActivity::class.java)
                activityTestRule.launchActivity(null)
                try {
                    base.evaluate() // This will run the test.
                } finally {
                    tearDown()
                }
            }
        }
    }

    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val sharedPrefs: SharedPreferences = context.getSharedPreferences("userdetails", Context.MODE_PRIVATE)
        val usernameKey = context.getString(R.string.sp_login_username)
        val passwordKey = context.getString(R.string.sp_login_password)
        val tokenKey = context.getString(R.string.sp_user_token)
        restoreUsername = sharedPrefs.getString(usernameKey, null)
        restorePassword = sharedPrefs.getString(passwordKey, null)
        restoreToken = sharedPrefs.getString(tokenKey, null)
        sharedPrefs.edit().clear().commit()
    }

    fun tearDown(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val sharedPrefs: SharedPreferences = context.getSharedPreferences("userdetails", Context.MODE_PRIVATE)
        val usernameKey = context.getString(R.string.sp_login_username)
        val passwordKey = context.getString(R.string.sp_login_password)
        val tokenKey = context.getString(R.string.sp_user_token)
        if (restoreUsername != null && restorePassword != null && restoreToken != null) {
            sharedPrefs.edit().putString(usernameKey, restoreUsername).apply()
            sharedPrefs.edit().putString(passwordKey, restorePassword).apply()
            sharedPrefs.edit().putString(tokenKey, restoreToken).apply()
        }
    }
}