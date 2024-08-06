package com.abztest.domain.di

import com.abztest.domain.usecases.token.GetAccessTokenUseCase
import com.abztest.domain.usecases.user.GetListUsersUseCase
import org.koin.dsl.module

private val useCasesModule = module {
    factory {
        GetAccessTokenUseCase(
            accessRepository = get()
        )
    }
    factory {
        GetListUsersUseCase(
            userRepository = get()
        )
    }

}

val domainModule = arrayOf(useCasesModule)