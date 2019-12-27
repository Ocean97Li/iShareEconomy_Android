package com.ocean.ishareeconomy_android.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ocean.ishareeconomy_android.network.services.LendingService
import com.ocean.ishareeconomy_android.network.services.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Part of *network*.
 *
 * Main entry point for network access. Call like `Network.users.getUsers(id, auth)`
 *
 * @property API_BASE_URL the base [String] to connect to the back-end server
 * @property retrofit the base [Retrofit] object that can create network services
 * @property users the [UserService] which makes the calls for stuff concerning users
 * @property lending the [LendingService] which makes the calls for stuff concerning lendingObjects
 */
object Network {
    private const val API_BASE_URL = "https://ishare-economy-backend.herokuapp.com/API/"
    private val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val users: UserService = retrofit.create(UserService::class.java)
    val lending: LendingService = retrofit.create(LendingService::class.java)
}