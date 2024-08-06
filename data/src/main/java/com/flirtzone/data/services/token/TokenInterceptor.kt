package com.flirtzone.data.services.token

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val tokenManger: TokenManger) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val accessToken = tokenManger.getAccessToken()
        val requestBuilder = original.newBuilder()

        accessToken?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}