package com.flirtzone.data.repositories

import com.abztest.domain.repositories.UserRepository

class UserRepositoryImpl : UserRepository {
    override suspend fun testUser(): Boolean {
        return true
    }
}