package ru.kode.tools.opengate.modules.gates.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import ru.kode.tools.opengate.core.BaseExecutor
import ru.kode.tools.opengate.core.Response

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
        is GatesStore.Intent.Open -> openGate(intent.id, intent.key)
    }

    private fun getGates() {
        val gates = repository.getGates()

        dispatch(StoreFactory.Message.SetData(gates))
    }

    private suspend fun openGate(id: String, key: String) {
        dispatch(StoreFactory.Message.SetGateState(OpenGateState(id,  OpenGateState.State.OPENING)))
        when (val response = repository.openGate(id, key)) {
            is Response.Success -> {
                val state = if (response.data) OpenGateState.State.OPENED else OpenGateState.State.ERROR
                dispatch(StoreFactory.Message.SetGateState(OpenGateState(id,  state)))
                dispatch(StoreFactory.Message.SetError(null))

                delay(1500L)

                dispatch(StoreFactory.Message.SetGateState(OpenGateState(id,  OpenGateState.State.PENDING)))
            }
            is Response.Failed -> {
                dispatch(StoreFactory.Message.SetGateState(OpenGateState(id,  OpenGateState.State.ERROR)))
                dispatch(StoreFactory.Message.SetError(response.throwable.message ?: "Error"))
            }
            else -> dispatch(StoreFactory.Message.SetError("Unknown error"))
        }
    }
}