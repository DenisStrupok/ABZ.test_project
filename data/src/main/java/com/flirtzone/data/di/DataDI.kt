package com.flirtzone.data.di

import com.abztest.domain.repositories.UserRepository
import com.flirtzone.data.repositories.UserRepositoryImpl
import org.koin.dsl.module

private val repositoriesModule = module {
    single<UserRepository> {
        UserRepositoryImpl()
    }
}

val dataModule = arrayOf(repositoriesModule)