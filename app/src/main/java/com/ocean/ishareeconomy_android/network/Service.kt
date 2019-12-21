package com.ocean.ishareeconomy_android.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ocean.ishareeconomy_android.network.services.LendingService
import com.ocean.ishareeconomy_android.network.services.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Main entry point for network access. Call like `Network.users.getUsers(id, auth)`
 */
object Network {
    private val API_BASE_URL = "https://ishare-economy-backend.herokuapp.com/API/"
    val retrofit = Retrofit
        .Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val users = retrofit.create(UserService::class.java)
    val lending = retrofit.create(LendingService::class.java)
}