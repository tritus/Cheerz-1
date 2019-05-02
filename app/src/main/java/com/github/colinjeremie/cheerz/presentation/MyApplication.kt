package com.github.colinjeremie.cheerz.presentation

import android.app.Application
import com.github.colinjeremie.cheerz.framework.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(presentersModule, networkModule, dataModule, storageModule, repositoryModule, useCasesModule)
        }
    }
}