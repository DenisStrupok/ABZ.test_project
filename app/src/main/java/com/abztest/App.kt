package com.abztest

import android.app.Application
import com.abztest.data.di.dataModules
import com.abztest.di.appModules
import com.abztest.domain.di.domainModules
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
                *appModules,
                *domainModules,
                *dataModules
            )
        }
    }
}