package com.ocean.ishareeconomy_android.network.services

import com.ocean.ishareeconomy_android.models.LendingObject
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface LendingService {

    @Headers("Content-type: application/json")
    @POST("users/{id}/lending")
    fun postLendObject(
        @Path("id") id: String,
        @Header("Authorization") auth: String,
        @Body lendObject: LendingObject
    ): Deferred<Response<LendingObject>>

}