package ru.kode.tools.opengate.routing.presentation

import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.states
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import ru.kode.tools.opengate.modules.auth.domain.AuthStore

class RootNavigatorViewModel(
    private val authStore: AuthStore,
) : ViewModel() {
    val auth: CStateFlow<AuthStore.State>
        get() = authState.cStateFlow()

    private val authState = MutableStateFlow(authStore.state).cMutableStateFlow()

    private val binder: Binder

    init {
        binder = bind(Dispatchers.Main.immediate) {
            authStore.states bindTo (::acceptState)
        }
        binder.start()
    }

    fun logout() = authStore.accept(AuthStore.Intent.Logout)

    private fun acceptState(state: AuthStore.State) {
        authState.value = state
    }

    override fun onCleared() {
        super.onCleared()
        binder.stop()
        authStore.dispose()
    }
}