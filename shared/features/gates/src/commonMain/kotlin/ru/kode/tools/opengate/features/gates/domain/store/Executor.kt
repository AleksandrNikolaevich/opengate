package ru.kode.tools.opengate.features.gates.domain.store

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import ru.kode.tools.opengate.features.gates.domain.Gate
import ru.kode.tools.opengate.foundation.core.BaseExecutor
import ru.kode.tools.opengate.foundation.core.Response
import ru.kode.tools.opengate.features.gates.domain.Repository

internal class Executor(
    private val repository: Repository,
) : BaseExecutor<GatesStore.Intent, Nothing, GatesStore.State, StoreFactory.Message, Nothing>(
    mainContext = Dispatchers.Main
) {
    override suspend fun suspendExecuteIntent(
        intent: GatesStore.Intent,
        getState: () -> GatesStore.State
    ) = when (intent) {
        is GatesStore.Intent.Init -> getGates()
        is GatesStore.Intent.GetGates -> getGates(intent.force)
        is GatesStore.Intent.Open -> openGate(intent.id, intent.key)
        is GatesStore.Intent.ChangeShortname -> setShortName(intent.id, intent.shortName)
        is GatesStore.Intent.Logout -> reset()
    }

    private suspend fun getGates(force: Boolean = false) {
        dispatch(StoreFactory.Message.SetLoading)

        when (val response = repository.getGates(force)) {
            is Response.Success -> {
                dispatch(StoreFactory.Message.SetData(response.data))
            }
            is Response.Cached -> {
                dispatch(StoreFactory.Message.SetData(response.data))
                getGates(true)
            }
            is Response.Failed -> {
                dispatch(StoreFactory.Message.SetError(response.throwable.message ?: "Error"))
            }
        }

    }

    private suspend fun openGate(id: String, key: String) {
        dispatch(StoreFactory.Message.SetGateState(id, Gate.OpenState.OPENING))
        when (val response = repository.openGate(id, key)) {
            is Response.Success -> {
                val state = if (response.data) Gate.OpenState.OPENED else Gate.OpenState.ERROR
                dispatch(StoreFactory.Message.SetGateState(id, state))
                dispatch(StoreFactory.Message.SetError(null))

                delay(1500L)

                dispatch(
                    StoreFactory.Message.SetGateState(
                        id,
                        Gate.OpenState.PENDING
                    )
                )
            }
            is Response.Failed -> {
                dispatch(
                    StoreFactory.Message.SetGateState(
                        id,
                        Gate.OpenState.ERROR
                    )
                )
                dispatch(StoreFactory.Message.SetError(response.throwable.message ?: "Error"))
            }
            else -> dispatch(StoreFactory.Message.SetError("Unknown error"))
        }
    }

    private suspend fun setShortName(id: String, shortName: String?) {
        repository.setShortName(id, shortName)
        dispatch(StoreFactory.Message.SetShortName(id, shortName))
    }

    private fun reset() {
        repository.clearData()
        dispatch(StoreFactory.Message.Reset)
    }
}