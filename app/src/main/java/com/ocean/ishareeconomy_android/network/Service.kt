package com.ocean.ishareeconomy_android.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ocean.ishareeconomy_android.models.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface UserService {

    @GET("users/{id}/users")
    fun getUsersAsync(@Path("id") id: String, @Header("Authorization") auth: String): Deferred<Response<List<User>>>

    @GET("users/{id}/users")
    fun getUsers(@Path("id") id: String, @Header("Authorization") auth: String): Call<List<User>>
}


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
}