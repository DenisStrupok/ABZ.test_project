package com.flirtzone.data.repositories

import com.abztest.domain.repositories.AccessRepository
import com.flirtzone.data.services.token.TokenManger
import com.flirtzone.data.services.token.TokenService

class AccessRepositoryImpl(
    private val tokenService: TokenService,
    private val tokenManager: TokenManger
) : AccessRepository {
    override suspend fun getAccessToken(): String {
        return if (!tokenManager.isTokenValid()) {
            val token = tokenService.getToken()
            tokenManager.saveAccessToken(token)
            token.token
        } else {
            tokenManager.getAccessToken() ?: ""
        }
    }
}