package com.abztest

import android.app.Application
import com.abztest.di.appModule
import com.abztest.domain.di.domainModule
import com.flirtzone.data.di.dataModule
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
                *appModule,
                *domainModule,
                *dataModule
            )
        }
    }
}