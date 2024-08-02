package com.abztest.domain.usecases

import com.abztest.domain.repositories.UserRepository
import com.abztest.domain.usecases.base.BaseUseCase

class TestUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<TestUseCase.Params, Boolean> {

    class Params

    override suspend fun invoke(params: Params): Boolean {
        return userRepository.testUser()
    }
}