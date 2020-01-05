package com.ocean.ishareeconomy_android.network.services

import com.ocean.ishareeconomy_android.models.User
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Part of *network.services*.
 *
 * Interface that defines all the API-request related to [User]
 */
interface UserService {

    /**
     * The method that defines the [GET]-request that fetches the users based on the supplied [id]
     *
     * @param id the logged in user's id, used as a [Path] parameter, determines the order of [User] objects returned, ordered by [User.distance]
     * @param auth the logged in user's authorization token, used as a [Header] parameter
     *
     * @return [Deferred]<[Response]<[List]<[User]>>> The async response containing the fetched [List]<[User]>
     */
    @GET("users/{id}/users")
    fun getUsersAsync(@Path("id") id: String, @Header("Authorization") auth: String): Deferred<Response<List<User>>>

    /**
     * The method that defines the [GET]-request that fetches the users based on the supplied [id]
     * and does it synchronously. This method is exclusively used at login, and is used to determine the validity of the users
     * auth token and fetch the needed data to start the app at the same time.
     *
     * @param id the logged in user's id, used as a [Path] parameter, determines the order of [User] objects returned, ordered by [User.distance]
     * @param auth the logged in user's authorization token, used as a [Header] parameter
     *
     * @return [Call]<[List]<[User]>> The synchronous response containing the fetched [List]<[User]>
     */
    @GET("users/{id}/users")
    fun getUsers(@Path("id") id: String, @Header("Authorization") auth: String): Call<List<User>>

}