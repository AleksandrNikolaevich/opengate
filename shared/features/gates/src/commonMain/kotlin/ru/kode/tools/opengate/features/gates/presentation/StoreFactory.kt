package ru.kode.tools.opengate.features.gates.presentation

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.kode.tools.opengate.features.gates.domain.Gate
import ru.kode.tools.opengate.features.gates.domain.OpenGateState
import ru.kode.tools.opengate.features.gates.domain.Repository

internal class StoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: Repository
) {
    fun create(): GatesStore = object :
        GatesStore,
        Store<GatesStore.Intent, GatesStore.State, Nothing> by storeFactory.create(
            name = GatesStore::class.simpleName,
            initialState = GatesStore.State(),
            bootstrapper = null,
            executorFactory = {
                Executor(
                    repository = repository,
                )
            },
            reducer = Reducer(),
        ) {}

    sealed interface Message {
        data object SetLoading : Message
        data class SetData(val gates: List<Gate>) : Message
        data class SetError(val error: String?) : Message
        data class SetGateState(val state: OpenGateState) : Message
        data object Reset : Message
    }
}