package com.flirtzone.data.repositories

import com.abztest.domain.models.UserModel
import com.abztest.domain.models.UserObjectModel
import com.abztest.domain.repositories.UserRepository
import com.abztest.domain.usecases.user.GetListUsersUseCase
import com.flirtzone.data.response.user.mapListToDomain
import com.flirtzone.data.response.user.mapObjectToDomain
import com.flirtzone.data.services.user.UserService

class UserRepositoryImpl(
    private val userService: UserService
) : UserRepository {

    override suspend fun getListUsers(params: GetListUsersUseCase.Params): UserObjectModel {
        val response = userService.getListUsers(
            page = params.page ?: 1,
            count = params.count ?: 6
        )
        return with(response) {
            mapObjectToDomain(this)
        }
    }
}