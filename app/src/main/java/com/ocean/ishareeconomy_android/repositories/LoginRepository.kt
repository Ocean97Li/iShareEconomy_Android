package com.ocean.ishareeconomy_android.repositories

import com.ocean.ishareeconomy_android.models.LoginObject
import com.ocean.ishareeconomy_android.models.LoginResponse

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.converter.gson.GsonConverterFactory

interface LoginRepository {

    @POST("users/login")
    fun login(
            @Body loggedInUser: LoginObject
    ): Call<LoginResponse>
}

object LoginAPI {
    private var API_BASE_URL = "https://ishare-economy-backend.herokuapp.com/API/"
    var repository = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_BASE_URL)
            .build()
            .create(LoginRepository::class.java)
}