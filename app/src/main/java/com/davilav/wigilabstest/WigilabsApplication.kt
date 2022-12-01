package com.davilav.wigilabstest

import android.app.Application
import com.davilav.wigilabstest.di.localModule
import com.davilav.wigilabstest.di.networkModule
import com.davilav.wigilabstest.di.repositoryModule
import com.davilav.wigilabstest.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WigilabsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WigilabsApplication)
            modules(arrayListOf(localModule, networkModule, repositoryModule, viewModelModule))
        }
    }
}