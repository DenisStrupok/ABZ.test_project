package com.abztest.domain.usecases.user

import com.abztest.domain.body.UserBody
import com.abztest.domain.models.UserRegistrationModel
import com.abztest.domain.repositories.UserRepository
import com.abztest.domain.usecases.base.BaseUseCase

class CreateUserUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<CreateUserUseCase.Params, UserRegistrationModel> {

    override suspend fun invoke(params: Params): UserRegistrationModel {
        return userRepository.createUser(params.params)
    }


    class Params(
        val params: UserBody
    )
}