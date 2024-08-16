package com.abztest.di

import com.abztest.features.add.AddUserVM
import com.abztest.features.home.HomeVM
import com.abztest.features.splash.SplashVM
import com.abztest.helper.NetworkConnection
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelsModule = module {
    viewModel {
        SplashVM(
            getAccessTokenUseCase = get()
        )
    }
    viewModel {
        HomeVM(
            getListUsersUseCase = get()
        )
    }
    viewModel {
        AddUserVM(
            getUserPositionsUseCase = get(),
            createUserUseCase = get()
        )
    }

    single {
        NetworkConnection(
            androidContext()
        )
    }
}

val appModule = arrayOf(viewModelsModule)