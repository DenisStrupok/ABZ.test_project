package com.abztest.data.di

import com.abztest.data.repositories.UserRepositoryImpl
import com.abztest.domain.repositories.UserRepository
import org.koin.dsl.module

private val repositoriesModule = module{
    single<UserRepository> {
        UserRepositoryImpl()
    }
}

val dataModules = arrayOf(repositoriesModule)