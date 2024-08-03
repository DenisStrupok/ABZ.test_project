package com.flirtzone.data.services.token

import com.flirtzone.data.response.token.TokenResponse
import retrofit2.http.GET

interface TokenService {

    @GET("token/")
    suspend fun getToken(): TokenResponse
}