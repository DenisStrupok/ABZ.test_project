package com.abztest.domain.repositories

import com.abztest.domain.body.UserBody
import com.abztest.domain.models.PositionModel
import com.abztest.domain.models.UserObjectModel
import com.abztest.domain.models.UserRegistrationModel
import com.abztest.domain.usecases.user.GetListUsersUseCase

interface UserRepository {

    suspend fun getListUsers(params: GetListUsersUseCase.Params): UserObjectModel?

    suspend fun getUserPositions(): List<PositionModel>

    suspend fun createUser(body: UserBody): UserRegistrationModel
}