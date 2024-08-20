package ru.kode.tools.opengate.app.domain

import ru.kode.tools.opengate.features.auth.presentation.AuthStore
import ru.kode.tools.opengate.features.gates.presentation.GatesStore

class AuthService(
    private val authStore: AuthStore,
    private val gatesStore: GatesStore,
) {
    fun logout() {
        authStore.accept(AuthStore.Intent.Logout)
        gatesStore.accept(GatesStore.Intent.Logout)
    }
}