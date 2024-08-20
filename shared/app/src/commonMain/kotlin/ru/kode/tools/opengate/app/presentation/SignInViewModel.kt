package ru.kode.tools.opengate.app.presentation

import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.states
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import ru.kode.tools.opengate.features.auth.domain.store.AuthStore
import kotlin.math.min

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

            login bindTo (::changeLogin)
        }
        binder.start()
    }

    fun signIn() = store.accept(AuthStore.Intent.SignIn(login.value, password.value))

    private fun changeLogin(login: String) {
        if (login.isEmpty()) {
            password.value = ""
            return
        }
        password.value = login
            .reversed()
            .slice(0..min(login.length - 1, 5))
            .reversed()
    }
    private fun acceptState(state: AuthStore.State) {
        mutableState.value = state
    }

    override fun onCleared() {
        super.onCleared()
        binder.stop()
        store.dispose()
    }
}