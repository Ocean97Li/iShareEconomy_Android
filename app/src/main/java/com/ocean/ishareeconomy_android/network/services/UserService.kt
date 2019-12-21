package com.ocean.ishareeconomy_android.network.services

import com.ocean.ishareeconomy_android.models.User
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserService {

    @GET("users/{id}/users")
    fun getUsersAsync(@Path("id") id: String, @Header("Authorization") auth: String): Deferred<Response<List<User>>>

    @GET("users/{id}/users")
    fun getUsers(@Path("id") id: String, @Header("Authorization") auth: String): Call<List<User>>

}