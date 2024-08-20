package ru.kode.tools.opengate.foundation.di

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import ru.kode.tools.opengate.foundation.core.DBDriverFactory
import ru.kode.tools.opengate.foundation.core.SecurityStorageFactory

actual fun initKoin(appDeclaration: KoinAppDeclaration, vararg modules: Module) {
    Napier.base(DebugAntilog())

    val listOfModules = modules.toMutableList()

    listOfModules.add(
        module {
            // iOS specific dependencies
            single<DBDriverFactory> {
                DBDriverFactory()
            }

            single<SecurityStorageFactory> {
                SecurityStorageFactory(
                    serviceName = "common",
                    accessGroup = null
                )
            }
        }
    )

    startKoin {
        appDeclaration()
        modules(listOfModules)
    }
}
