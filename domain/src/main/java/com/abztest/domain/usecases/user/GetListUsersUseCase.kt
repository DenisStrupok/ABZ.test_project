package com.abztest.domain.usecases.user

import com.abztest.domain.models.UserModel
import com.abztest.domain.models.UserObjectModel
import com.abztest.domain.repositories.UserRepository
import com.abztest.domain.usecases.base.BaseUseCase

class GetListUsersUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<GetListUsersUseCase.Params, UserObjectModel?> {

    override suspend fun invoke(params: Params): UserObjectModel? {
        return userRepository.getListUsers(params = params)
    }


    class Params(
        val page: Int?,
        val count: Int?
    )
}