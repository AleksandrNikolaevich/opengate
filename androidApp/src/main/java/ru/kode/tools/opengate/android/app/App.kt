package ru.kode.tools.opengate.android.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import ru.kode.tools.opengate.di.initKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(applicationContext)
        }
    }
}