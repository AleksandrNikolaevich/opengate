package ru.kode.tools.opengate.domain

import ru.kode.tools.opengate.modules.auth.presentation.AuthStore
import ru.kode.tools.opengate.modules.gates.presentation.GatesStore

class AuthService(
    private val authStore: AuthStore,
    private val gatesStore: GatesStore,
) {
    fun logout() {
        authStore.accept(AuthStore.Intent.Logout)
        gatesStore.accept(GatesStore.Intent.Logout)
    }
}