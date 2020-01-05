package com.ocean.ishareeconomy_android.network.services

import com.ocean.ishareeconomy_android.models.LoginObject
import com.ocean.ishareeconomy_android.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("users/login")
    fun login(@Body loggedInUser: LoginObject): Call<LoginResponse>

}