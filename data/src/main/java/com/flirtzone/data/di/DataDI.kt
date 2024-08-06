package com.flirtzone.data.di

import com.abztest.domain.repositories.AccessRepository
import com.abztest.domain.repositories.UserRepository
import com.flirtzone.data.repositories.AccessRepositoryImpl
import com.flirtzone.data.repositories.UserRepositoryImpl
import com.flirtzone.data.services.token.TokenInterceptor
import com.flirtzone.data.services.token.TokenManger
import com.flirtzone.data.services.token.TokenService
import com.flirtzone.data.services.user.UserService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val repositoriesModule = module {
    single<UserRepository> {
        UserRepositoryImpl(
            userService = get()
        )
    }
    single<AccessRepository> {
        AccessRepositoryImpl(
            tokenService = get(),
            tokenManager = get()
        )
    }
}

private val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(get()))
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://frontend-test-assignment-api.abz.agency/api/v1/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    single<TokenService> {
        get<Retrofit>().create(TokenService::class.java)
    }
    single<UserService> {
        get<Retrofit>().create(UserService::class.java)
    }
    single {
        TokenManger(androidContext())
    }
}

val dataModule = arrayOf(repositoriesModule, networkModule)