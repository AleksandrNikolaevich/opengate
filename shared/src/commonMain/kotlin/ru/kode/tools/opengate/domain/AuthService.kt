package ru.kode.tools.opengate.domain

import ru.kode.tools.opengate.data.Api
import ru.kode.tools.opengate.modules.auth.domain.AuthStore
import ru.kode.tools.opengate.modules.gates.domain.GatesStore

class AuthService(
    private val authStore: AuthStore,
    private val gatesStore: GatesStore,
) {
    fun logout() {
        authStore.accept(AuthStore.Intent.Logout)
        gatesStore.accept(GatesStore.Intent.Logout)
    }
}