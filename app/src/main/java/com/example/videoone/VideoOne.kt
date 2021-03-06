package com.example.videoone

import android.app.Application
import com.example.videoone.model.videoRepositoryModule
import com.example.videoone.network.networkModule
import com.example.videoone.ui.main.mainViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class VideoOne : Application(){
    override fun onCreate() {
        super.onCreate()

        // init Koin
        startKoin{
            androidLogger()
            androidContext(this@VideoOne)
            modules(listOf(networkModule, videoRepositoryModule, mainViewModelModule))
        }
    }
}