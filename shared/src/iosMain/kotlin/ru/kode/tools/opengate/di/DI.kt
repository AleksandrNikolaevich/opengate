package ru.kode.tools.opengate.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import ru.kode.tools.opengate.presentation.ProfileViewModel
import ru.kode.tools.opengate.presentation.SignInViewModel
import ru.kode.tools.opengate.presentation.RootNavigatorViewModel

class DI : KoinComponent {
    fun signInViewModel(): SignInViewModel = get()
    fun rootNavigatorViewModel(): RootNavigatorViewModel = get()
    fun profileViewModel(): ProfileViewModel = get()
}