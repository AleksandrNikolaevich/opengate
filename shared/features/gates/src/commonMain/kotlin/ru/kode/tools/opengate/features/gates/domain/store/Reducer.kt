package ru.kode.tools.opengate.features.gates.domain.store

internal class Reducer : com.arkivanov.mvikotlin.core.store.Reducer<GatesStore.State, StoreFactory.Message> {
    override fun GatesStore.State.reduce(msg: StoreFactory.Message): GatesStore.State {
        return when (msg) {
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
                if (gates == null) {
                    return copy()
                }

                val newGatesList = gates.map { gate ->
                    if (gate.id == msg.gateId) {
                        return@map gate.copy(state = msg.state)
                    }

                    return@map gate
                }

                copy(
                    gates = newGatesList,
                )
            }

            is StoreFactory.Message.SetShortName -> {
                if (gates == null) {
                    return copy()
                }

                val newGatesList = gates.map { gate ->
                    if (gate.id == msg.gateId) {
                        return@map gate.copy(shortName = msg.shortName)
                    }

                    return@map gate
                }

                copy(
                    gates = newGatesList,
                )
            }

            is StoreFactory.Message.Reset -> {
                GatesStore.State()
            }
        }
    }
}