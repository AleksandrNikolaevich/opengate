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
    private val mutableState = MutableStateFlow(store.state).cMutableStateFlow()

    private val binder: Binder

    val state: CStateFlow<AuthStore.State>
        get() = mutableState.cStateFlow()

    var login = MutableStateFlow("").cMutableStateFlow()
    var password = MutableStateFlow("").cMutableStateFlow()

    init {
        binder = bind(Dispatchers.Main.immediate) {
            store.states bindTo (::acceptState)
        }
        binder.start()
    }

    fun signIn() = store.accept(AuthStore.Intent.SignIn(login.value, password.value))

    private fun acceptState(state: AuthStore.State) {
        mutableState.value = state
    }

    override fun onCleared() {
        super.onCleared()
        binder.stop()
        store.dispose()
    }
}