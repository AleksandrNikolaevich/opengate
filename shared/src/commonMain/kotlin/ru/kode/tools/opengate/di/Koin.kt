package ru.kode.tools.opengate.di

import org.koin.dsl.KoinAppDeclaration

expect fun initKoin(appDeclaration: KoinAppDeclaration = {})

object KoinHelper {
    fun init() {
        initKoin()
    }
}