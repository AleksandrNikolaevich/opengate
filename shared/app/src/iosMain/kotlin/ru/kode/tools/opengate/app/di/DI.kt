package ru.kode.tools.opengate.app.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import ru.kode.tools.opengate.app.presentation.HomeViewModel
import ru.kode.tools.opengate.app.presentation.ProfileViewModel
import ru.kode.tools.opengate.app.presentation.SignInViewModel
import ru.kode.tools.opengate.app.presentation.RootNavigatorViewModel

class DI : KoinComponent {
    fun signInViewModel(): SignInViewModel = get()
    fun rootNavigatorViewModel(): RootNavigatorViewModel = get()
    fun profileViewModel(): ProfileViewModel = get()
    fun homeViewModel(): HomeViewModel = get()
}