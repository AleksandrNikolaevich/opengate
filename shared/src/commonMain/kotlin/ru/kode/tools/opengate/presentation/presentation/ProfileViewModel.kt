package ru.kode.tools.opengate.presentation.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import ru.kode.tools.opengate.modules.auth.domain.AuthStore

class ProfileViewModel(private val store: AuthStore) : ViewModel() {
    fun logout() = store.accept(AuthStore.Intent.Logout)
}