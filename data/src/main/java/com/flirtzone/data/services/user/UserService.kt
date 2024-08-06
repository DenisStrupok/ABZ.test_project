package com.flirtzone.data.services.user

import com.flirtzone.data.response.user.UsersObjectResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("users")
    suspend fun getListUsers(
        @Query("page") page: Int,
        @Query("count") count: Int
    ): UsersObjectResponse
}