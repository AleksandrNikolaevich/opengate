package ru.kode.tools.opengate.features.gates.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import ru.kode.tools.opengate.features.gates.domain.Gate

interface GatesStore : Store<GatesStore.Intent, GatesStore.State, Nothing > {
    data class State internal constructor(
        val isLoading: Boolean = false,
        val error: String? = null,
        val gates: List<Gate>? = null,
    )

    sealed interface Intent {
        data object Init : Intent
        data class GetGates(val force: Boolean) : Intent
        data class Open(val id: String, val key: String) : Intent
        data class ChangeShortname(val id: String, val shortName: String?): Intent
        data object Logout : Intent
    }
}