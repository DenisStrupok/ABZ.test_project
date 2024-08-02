package com.abztest

import android.app.Application
import com.abztest.di.appDI
import com.abztest.domain.di.domainDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                *appDI,
                *domainDI
            )
        }
    }
}