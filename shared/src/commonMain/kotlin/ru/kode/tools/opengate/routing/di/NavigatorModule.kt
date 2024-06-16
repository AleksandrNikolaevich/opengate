package ru.kode.tools.opengate.routing.di

import org.koin.dsl.module
import ru.kode.tools.opengate.routing.presentation.RootNavigatorViewModel

internal var navigatorModule = module {
    single {
        RootNavigatorViewModel(
            authStore = get()
        )
    }
}