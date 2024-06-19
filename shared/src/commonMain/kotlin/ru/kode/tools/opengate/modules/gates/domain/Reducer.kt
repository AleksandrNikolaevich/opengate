package ru.kode.tools.opengate.modules.gates.domain

import ru.kode.tools.opengate.modules.gates.domain.GatesStore
import ru.kode.tools.opengate.modules.gates.domain.StoreFactory

internal class Reducer : com.arkivanov.mvikotlin.core.store.Reducer<GatesStore.State, StoreFactory.Message> {
    override fun GatesStore.State.reduce(msg: StoreFactory.Message): GatesStore.State =
        when (msg) {
            is StoreFactory.Message.SetLoading -> copy(
                isLoading = true,
                error = null,
            )

            is StoreFactory.Message.SetData -> copy(
                isLoading = false,
                gates = msg.gates
            )

            is StoreFactory.Message.SetError -> copy(
                isLoading = false,
                error = msg.error
            )

            is StoreFactory.Message.SetGateState -> {
                val newListStates = openStates.toMutableList()
                val lastState = newListStates.find { item -> item.id == msg.state.id }

                newListStates.remove(lastState)

                newListStates.add(msg.state)

                copy(
                    openStates = newListStates.toList(),
                )
            }
        }
}