package com.abztest.domain.repositories

interface AccessRepository {

    suspend fun getAccessToken(): String
}