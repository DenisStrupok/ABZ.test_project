package com.abztest.domain.usecases.user

import com.abztest.domain.models.PositionModel
import com.abztest.domain.repositories.UserRepository
import com.abztest.domain.usecases.base.BaseUseCase

class GetUserPositionsUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<Unit, List<PositionModel>> {

    override suspend fun invoke(params: Unit): List<PositionModel> {
        return userRepository.getUserPositions()
    }
}