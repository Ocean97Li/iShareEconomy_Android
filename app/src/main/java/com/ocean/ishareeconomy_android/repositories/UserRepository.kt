package com.ocean.ishareeconomy_android.repositories

import com.ocean.ishareeconomy_android.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface UserRepository {

    @GET("users/{id}/users")
    fun getUsers(@Path("id") id: String, @Header("Authorization") auth: String): Call<List<User>>
}

object UserAPI {
    private var API_BASE_URL = "https://ishare-economy-backend.herokuapp.com/API/"
    var repository = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(API_BASE_URL)
        .build()
        .create(UserRepository::class.java)
}