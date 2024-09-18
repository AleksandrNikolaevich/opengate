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
import ru.kode.tools.opengate.features.gates.domain.Gate
import ru.kode.tools.opengate.features.gates.domain.store.GatesStore
import ru.kode.tools.opengate.foundation.core.map

class GateViewModel(
    private val store: GatesStore
) : ViewModel() {
    private val mutableState = MutableStateFlow(store.state).cMutableStateFlow()

    private val binder: Binder

    private var gateId: String = ""

    val shortNameFieldValue = MutableStateFlow<String?>(null).cMutableStateFlow()

    val state: CStateFlow<State>
        get() = mutableState
            .cStateFlow()
            .map(this.viewModelScope) { state ->
                val gate = findGate(state.gates)
                State(
                    isLoading = state.isLoading,
                    error = state.error,
                    gate = gate,
                    shortName = gate?.shortName ?:
                                gate?.name
                )
            }

    init {
        binder = bind(Dispatchers.Main.immediate) {
            store.states bindTo (::acceptState)
        }
        binder.start()
    }

    fun commitShortName() = run {
        shortNameFieldValue.value = shortNameFieldValue.value?.ifEmpty { null }
        store.accept(
            GatesStore.Intent.ChangeShortname(
                gateId,
                shortNameFieldValue.value
            )
        )
    }

    fun rollbackShortName() = setDefaultShortName()

    fun openGate() = run {
        val gate = state.value.gate ?: return@run
        store.accept(GatesStore.Intent.Open(gate.id, gate.key))
    }

    fun run(gateId: String) = run {
        rollbackShortName()
        this.gateId = gateId
        store.accept(GatesStore.Intent.Init)
    }

    private fun acceptState(state: GatesStore.State) {
        mutableState.value = state
        setDefaultShortName()
    }

    private fun setDefaultShortName() {
        val gate = findGate(mutableState.value.gates)
        if (shortNameFieldValue.value.isNullOrEmpty()) {
            shortNameFieldValue.value = gate?.shortName ?: gate?.name
        }
    }

    private fun findGate(gates: List<Gate>?) : Gate? {
        return gates?.find { gate -> gate.id == gateId }
    }

    override fun onCleared() {
        super.onCleared()
        binder.stop()
        store.dispose()
    }
}

data class State internal constructor(
    val isLoading: Boolean = false,
    val error: String? = null,
    val gate: Gate? = null,
    val shortName: String? = null,
)
