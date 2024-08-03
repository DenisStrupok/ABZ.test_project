package com.abztest.domain.usecases.token

import com.abztest.domain.repositories.AccessRepository
import com.abztest.domain.usecases.base.BaseUseCase

class GetAccessTokenUseCase(
    private val accessRepository: AccessRepository
): BaseUseCase<Unit, String> {
    override suspend fun invoke(params: Unit): String {
       return accessRepository.getAccessToken()
    }
}