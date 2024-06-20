package ru.kode.tools.opengate.di

import org.koin.dsl.module
import ru.kode.tools.opengate.domain.AuthService
import ru.kode.tools.opengate.presentation.HomeViewModel
import ru.kode.tools.opengate.presentation.ProfileViewModel
import ru.kode.tools.opengate.presentation.RootNavigatorViewModel
import ru.kode.tools.opengate.presentation.SignInViewModel

internal var viewModels = module {
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