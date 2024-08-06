package com.abztest.domain.repositories

import com.abztest.domain.models.UserModel
import com.abztest.domain.models.UserObjectModel
import com.abztest.domain.usecases.user.GetListUsersUseCase

interface UserRepository {

    suspend fun getListUsers(params: GetListUsersUseCase.Params): UserObjectModel?
}