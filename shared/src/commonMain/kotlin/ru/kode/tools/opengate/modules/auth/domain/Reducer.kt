package ru.kode.tools.opengate.modules.auth.domain

internal class Reducer : com.arkivanov.mvikotlin.core.store.Reducer<AuthStore.State, StoreFactory.Message> {
    override fun AuthStore.State.reduce(msg: StoreFactory.Message): AuthStore.State =
        when (msg) {
            is StoreFactory.Message.SetError -> copy(
                error = msg.message,
                isLoading = false,
            )

            is StoreFactory.Message.SetLoggedIn -> copy(
                isLoggedIn = msg.state,
                isLoading = false,
            )

            is StoreFactory.Message.SetLoading -> copy(
                isLoading = true,
                error = null,
            )
        }
}