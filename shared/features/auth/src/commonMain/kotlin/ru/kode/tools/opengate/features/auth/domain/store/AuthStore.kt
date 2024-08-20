package ru.kode.tools.opengate.features.auth.domain.store

import com.arkivanov.mvikotlin.core.store.Store

interface AuthStore : Store<AuthStore.Intent, AuthStore.State, Nothing> {
    data class State internal constructor(
        val isLoggedIn: Boolean? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
    )

    sealed interface Intent {
        class SignIn(val login: String, val password: String) : Intent
        data object Logout : Intent
        data object CheckLogin : Intent
    }
}