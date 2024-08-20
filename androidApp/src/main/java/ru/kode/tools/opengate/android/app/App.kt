package ru.kode.tools.opengate.android.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import ru.kode.tools.opengate.app.di.KoinHelper

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinHelper.init {
            androidContext(applicationContext)
        }
    }
}