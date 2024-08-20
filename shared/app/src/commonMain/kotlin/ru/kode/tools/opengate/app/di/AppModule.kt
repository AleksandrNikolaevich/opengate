package ru.kode.tools.opengate.app.di

import org.koin.dsl.module
import ru.kode.tools.opengate.app.presentation.HomeViewModel
import ru.kode.tools.opengate.app.presentation.ProfileViewModel
import ru.kode.tools.opengate.app.presentation.RootNavigatorViewModel
import ru.kode.tools.opengate.app.presentation.SignInViewModel
import ru.kode.tools.opengate.app.domain.AuthService

internal var appModule = module {
    single {
        AuthService(
            authStore = get(),
            gatesStore = get()
        )
    }

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
            authService = get()
        )
    }

    single {
        HomeViewModel(
            store = get()
        )
    }
}