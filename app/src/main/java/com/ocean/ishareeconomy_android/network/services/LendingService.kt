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

    @Headers("Content-type: application/json")
    @DELETE("users/{user_id}/lending/{object_id}")
    fun deleteLendObject(
        @Path("user_id") userId: String,
        @Path("object_id") objectId: String,
        @Header("Authorization") auth: String
    ): Deferred<Response<LendingObject>>

}