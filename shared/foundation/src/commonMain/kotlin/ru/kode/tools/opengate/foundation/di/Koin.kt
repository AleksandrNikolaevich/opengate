package ru.kode.tools.opengate.foundation.di

import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

expect fun initKoin(appDeclaration: KoinAppDeclaration = {}, vararg modules: Module)

object KoinHelper {
    fun init(appDeclaration: KoinAppDeclaration = {}, vararg modules: Module) {

        val listOfModules = modules.toMutableList()

        listOfModules.add(commonModule)

        initKoin(
            appDeclaration = appDeclaration,
            *listOfModules.map { it }.toTypedArray()
        )
    }
}