package ru.kode.tools.opengate.presentation.presentation

import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.states
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import ru.kode.tools.opengate.modules.gates.domain.Gate
import ru.kode.tools.opengate.modules.gates.domain.GatesStore

class HomeViewModel(
    private val store: GatesStore
) : ViewModel() {
    private val mutableState = MutableStateFlow(store.state).cMutableStateFlow()

    private val binder: Binder

    val state: CStateFlow<GatesStore.State>
        get() = mutableState.cStateFlow()

    init {
        binder = bind(Dispatchers.Main.immediate) {
            store.states bindTo (::acceptState)
        }
        binder.start()

        store.accept(GatesStore.Intent.Init)
    }

    fun openGate(gate: Gate) = store.accept(GatesStore.Intent.Open(gate.id, gate.key))

    private fun acceptState(state: GatesStore.State) {
        mutableState.value = state
    }

    override fun onCleared() {
        super.onCleared()
        binder.stop()
        store.dispose()
    }
}