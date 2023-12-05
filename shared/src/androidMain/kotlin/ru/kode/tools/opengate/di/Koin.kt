package ru.kode.tools.opengate.di

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import ru.kode.tools.opengate.core.DBDriverFactory
import ru.kode.tools.opengate.modules.auth.di.authModule

actual fun initKoin(appDeclaration: KoinAppDeclaration, vararg modules: Module) {
    Napier.base(DebugAntilog())

    val listOfModules = modules.toMutableList()

    listOfModules.add(
        module {
            // Android specific dependencies
            single {
                DBDriverFactory(
                    context = get()
                )
            }
        }
    )

    startKoin {
        appDeclaration()
        modules(listOfModules)
    }
}