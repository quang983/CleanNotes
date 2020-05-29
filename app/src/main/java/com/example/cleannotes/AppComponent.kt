package com.example.cleannotes

import android.app.Application
import com.example.data.di.mapperModule
import com.example.data.di.repositoryModule
import com.example.domain.di.useCaseModule
import com.example.local.di.databaseModule
import com.example.local.di.localSourceModule
import com.example.cleannotes.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppComponent: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@AppComponent)
            modules(
                useCaseModule,
                localSourceModule,
                databaseModule,
                mapperModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}