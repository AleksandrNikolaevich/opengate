package ru.kode.tools.opengate.di

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

actual fun initKoin(appDeclaration: KoinAppDeclaration) {
    Napier.base(DebugAntilog())

    startKoin {
        appDeclaration()
        modules(
            commonModule,
        )
    }
}