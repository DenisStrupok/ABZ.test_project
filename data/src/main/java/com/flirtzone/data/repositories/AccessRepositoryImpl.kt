package com.flirtzone.data.repositories

import com.abztest.domain.repositories.AccessRepository
import com.flirtzone.data.services.token.TokenManger
import com.flirtzone.data.services.token.TokenService

class AccessRepositoryImpl(
    private val tokenService: TokenService,
    private val tokenManager: TokenManger
) : AccessRepository {
    override suspend fun getAccessToken(): String {
        if (tokenManager.isTokenValid()) {
            val test = tokenManager.getAccessToken()
            test.toString()
        } else {
            tokenManager.saveAccessToken(tokenService.getToken())
        }
        return "Check data"
    }
}