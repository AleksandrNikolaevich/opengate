package ru.kode.tools.opengate.app.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import ru.kode.tools.opengate.app.domain.AuthService

class ProfileViewModel(private val authService: AuthService) : ViewModel() {
    fun logout() = authService.logout()
}