package com.ocean.ishareeconomy_android.network.services

import com.ocean.ishareeconomy_android.models.LendingObject
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface LendingService {

    @POST("users/{id}/lending")
    fun postLendObject(@Path("id") id: String, @Header("Authorization") auth: String): Deferred<Response<LendingObject>>

}