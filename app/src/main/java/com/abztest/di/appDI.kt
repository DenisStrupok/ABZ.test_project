package com.abztest.di

import com.abztest.features.add.AddUserVM
import com.abztest.features.users.UsersVM
import com.abztest.features.main.MainVM
import com.abztest.features.splash.SplashVM
import com.abztest.helper.NetworkConnection
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelsModule = module {
    viewModel {
        MainVM()
    }
    viewModel {
        SplashVM(
            getAccessTokenUseCase = get()
        )
    }
    viewModel {
        UsersVM(
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