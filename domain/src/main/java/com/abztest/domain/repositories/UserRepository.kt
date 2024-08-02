package com.abztest.domain.repositories

interface UserRepository {

    suspend fun testUser(): Boolean
}