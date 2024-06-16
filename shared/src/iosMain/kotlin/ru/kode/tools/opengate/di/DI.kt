package ru.kode.tools.opengate.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import ru.kode.tools.opengate.modules.auth.presentation.SignInViewModel
import ru.kode.tools.opengate.routing.presentation.RootNavigatorViewModel

class DI : KoinComponent {
    fun signInViewModel(): SignInViewModel = get()
    fun rootNavigatorViewModel(): RootNavigatorViewModel = get()
}