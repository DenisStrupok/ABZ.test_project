package com.flirtzone.data.services.user

import com.abztest.domain.body.UserBody
import com.flirtzone.data.response.user.PositionObjectResponse
import com.flirtzone.data.response.user.UserRegistrationResponse
import com.flirtzone.data.response.user.UsersObjectResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface UserService {

    @GET("users")
    suspend fun getListUsers(
        @Query("page") page: Int,
        @Query("count") count: Int
    ): UsersObjectResponse

    @GET("positions")
    suspend fun getUserPositions(): PositionObjectResponse

    @Multipart
    @POST("users")
    suspend fun createUser(
        @Header("Token") token: String,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("position_id") positionId: RequestBody,
        @Part photo: MultipartBody.Part
    ): UserRegistrationResponse
}