package ru.kode.tools.opengate.presentation.di

import org.koin.dsl.module
import ru.kode.tools.opengate.presentation.presentation.HomeViewModel
import ru.kode.tools.opengate.presentation.presentation.ProfileViewModel
import ru.kode.tools.opengate.presentation.presentation.RootNavigatorViewModel
import ru.kode.tools.opengate.presentation.presentation.SignInViewModel

internal var presentation = module {
    single {
        RootNavigatorViewModel(
            authStore = get()
        )
    }

    single {
        SignInViewModel(
            store = get()
        )
    }

    single {
        ProfileViewModel(
            store = get()
        )
    }

    single {
        HomeViewModel(
            store = get()
        )
    }
}