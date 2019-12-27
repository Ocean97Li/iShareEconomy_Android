package com.ocean.ishareeconomy_android

import android.app.Application
import android.content.Intent
import com.jakewharton.threetenabp.AndroidThreeTen
import com.ocean.ishareeconomy_android.login.LoginActivity
/**
 * Part of *com.ocean.ishareeconomy_android*.
 *
 * MyApplication is a custom application class
 * which main use is that it initializes the [AndroidThreeTen] library
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
