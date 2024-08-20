package ru.kode.tools.opengate.app.di

import org.koin.dsl.KoinAppDeclaration
import ru.kode.tools.opengate.features.auth.di.authModule
import ru.kode.tools.opengate.features.gates.di.gatesModule

object KoinHelper {
    fun init(appDeclaration: KoinAppDeclaration = {}) {
        ru.kode.tools.opengate.foundation.di.KoinHelper.init(appDeclaration, appModule, authModule, gatesModule)
    }
}