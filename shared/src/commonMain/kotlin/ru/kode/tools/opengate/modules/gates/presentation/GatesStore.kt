package ru.kode.tools.opengate.modules.gates.presentation

import com.arkivanov.mvikotlin.core.store.Store
import ru.kode.tools.opengate.modules.gates.domain.Gate
import ru.kode.tools.opengate.modules.gates.domain.OpenGateState

interface GatesStore : Store<GatesStore.Intent, GatesStore.State, Nothing > {
    data class State internal constructor(
        val isLoading: Boolean = false,
        val error: String? = null,
        val gates: List<Gate>? = null,
        val openStates: List<OpenGateState> = listOf()
    )

    sealed interface Intent {
        data object Init : Intent
        data class GetGates(val force: Boolean) : Intent
        data class Open(val id: String, val key: String) : Intent
        data object Logout : Intent
    }
}