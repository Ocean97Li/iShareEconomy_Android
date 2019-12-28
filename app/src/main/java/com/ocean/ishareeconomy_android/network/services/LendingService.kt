package com.ocean.ishareeconomy_android.network.services

import com.ocean.ishareeconomy_android.models.LendingObject
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

/**
 * Part of *network.services*.
 *
 * Interface that defines all the API-request related to [LendingObject]
 */
interface LendingService {

    @Headers("Content-type: application/json")
    @POST("users/{id}/lending")
    fun postLendObjectAsync(
        @Path("id") id: String,
        @Header("Authorization") auth: String,
        @Body lendObject: LendingObject
    ): Deferred<Response<LendingObject>>

    @Headers("Content-type: application/json")
    @DELETE("users/{user_id}/lending/{object_id}")
    fun deleteLendObjectAsync(
        @Path("user_id") userId: String,
        @Path("object_id") objectId: String,
        @Header("Authorization") auth: String
    ): Deferred<Response<LendingObject>>

}