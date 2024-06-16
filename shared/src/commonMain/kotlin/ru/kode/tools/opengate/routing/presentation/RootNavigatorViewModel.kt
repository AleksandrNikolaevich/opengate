package ru.kode.tools.opengate.routing.presentation

import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.states
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import ru.kode.tools.opengate.modules.auth.domain.AuthStore

class RootNavigatorViewModel(
    private val authStore: AuthStore,
) : ViewModel() {
    private val _state = MutableStateFlow(AppState.PENDING).cMutableStateFlow()
    private val binder: Binder

    val appState: CStateFlow<AppState>
        get() = _state.cStateFlow()

    init {
        binder = bind(Dispatchers.Main.immediate) {
            authStore.states bindTo (::acceptAuthState)
        }
        binder.start()

        authStore.accept(AuthStore.Intent.CheckLogin)
    }

    fun logout() = authStore.accept(AuthStore.Intent.Logout)

    private fun acceptAuthState(state: AuthStore.State) {
        val isLoggedIn = state.isLoggedIn

        if (isLoggedIn == null) {
            _state.value = AppState.PENDING
            return
        }

        _state.value = if (isLoggedIn) AppState.AUTHENTICATED else AppState.NEED_AUTH
    }

    override fun onCleared() {
        super.onCleared()
        binder.stop()
        authStore.dispose()
    }

    enum class AppState {
        PENDING, NEED_AUTH, AUTHENTICATED
    }
}