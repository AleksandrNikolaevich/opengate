package ru.kode.tools.opengate.modules.auth.presentation

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

class SignInViewModel(
    private val store: AuthStore,
) : ViewModel() {
    val state: CStateFlow<AuthStore.State>
        get() = mutableState.cStateFlow()

    private val initialState = AuthStore.State()

    private val mutableState = MutableStateFlow(initialState).cMutableStateFlow()

    private val binder: Binder

    init {
        binder = bind(Dispatchers.Main.immediate) {
            store.states bindTo (::acceptState)
        }
        binder.start()
    }

    fun signIn(login: String, password: String) = store.accept(AuthStore.Intent.SignIn(login, password))

    private fun acceptState(state: AuthStore.State) {
        mutableState.value = state
    }

    override fun onCleared() {
        super.onCleared()
        binder.stop()
        store.dispose()
    }
}