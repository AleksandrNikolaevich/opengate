package ru.kode.tools.opengate.di

import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import ru.kode.tools.opengate.modules.auth.di.authModule
import ru.kode.tools.opengate.modules.gates.di.gatesModule

expect fun initKoin(appDeclaration: KoinAppDeclaration = {}, vararg modules: Module)

object KoinHelper {
    fun init(appDeclaration: KoinAppDeclaration = {}) {
        initKoin(
            appDeclaration = appDeclaration,
            commonModule,
            authModule,
            gatesModule,
            viewModels,
        )
    }
}