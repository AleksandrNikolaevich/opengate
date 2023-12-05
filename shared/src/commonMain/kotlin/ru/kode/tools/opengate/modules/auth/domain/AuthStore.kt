package ru.kode.tools.opengate.modules.auth.domain

import com.arkivanov.mvikotlin.core.store.Store

interface AuthStore : Store<AuthStore.Intent, AuthStore.State, Nothing> {
    data class State internal constructor(
        val isLoggedIn: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null,
    )

    sealed interface Intent {
        class SignIn(val login: String, val password: String) : Intent
    }
}