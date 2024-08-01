package com.abztest.di

import com.abztest.features.splash.SplashVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelsModule = module {
    viewModel {
        SplashVM(
            testUseCase = get()
        )
    }
}

val appDI = arrayOf(viewModelsModule)