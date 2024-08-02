package com.abztest.domain.di

import com.abztest.domain.usecases.TestUseCase
import org.koin.dsl.module

private val useCasesModule = module {
    factory { TestUseCase() }

}

val domainDI = arrayOf(useCasesModule)