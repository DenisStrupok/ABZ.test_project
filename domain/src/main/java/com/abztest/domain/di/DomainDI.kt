package com.abztest.domain.di

import com.abztest.domain.usecases.token.GetAccessTokenUseCase
import org.koin.dsl.module

private val useCasesModule = module {
    factory {
        GetAccessTokenUseCase(
            accessRepository = get()
        )
    }

}

val domainModule = arrayOf(useCasesModule)