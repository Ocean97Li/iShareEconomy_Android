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

    /**
     * The method that defines the [POST]-request that creates a [LendingObject]
     *
     * @param id the logged in user's id, used as a [Path] parameter
     * @param auth the logged in user's authorization token, used as a [Header] parameter
     * @param lendObject the created [LendingObject], used a a [Body] parameter
     *
     * @return [Deferred]<[Response]<[LendingObject]>> The async response containing the created lendobject
     */
    @Headers("Content-type: application/json")
    @POST("users/{id}/lending")
    fun postLendObjectAsync(
        @Path("id") id: String,
        @Header("Authorization") auth: String,
        @Body lendObject: LendingObject
    ): Deferred<Response<LendingObject>>

    /**
     * The method that defines the [DELETE]-request that deletes a [LendingObject]
     *
     * @param userId the logged in user's id, used as a [Path] parameter
     * @param objectId the to be deleted [LendingObject]'s id, used as a [Path] parameter
     * @param auth the logged in user's authorization token, used as a [Header] parameter
     *
     * @return [Deferred]<[Response]<[LendingObject]>> The async response containing the deleted lendobject
     */
    @Headers("Content-type: application/json")
    @DELETE("users/{user_id}/lending/{object_id}")
    fun deleteLendObjectAsync(
        @Path("user_id") userId: String,
        @Path("object_id") objectId: String,
        @Header("Authorization") auth: String
    ): Deferred<Response<LendingObject>>

}