package ru.kode.tools.opengate.foundation.core

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseExecutor<in Intent : Any, in Action : Any, in State : Any, Message : Any, Label : Any>(
    mainContext: CoroutineContext,
) : CoroutineExecutor<Intent, Action, State, Message, Label>(mainContext = mainContext) {
    final override fun executeIntent(intent: Intent, getState: () -> State) {
        scope.launch {
            suspendExecuteIntent(intent, getState)
        }
    }

    final override fun executeAction(action: Action, getState: () -> State) {
        scope.launch {
            suspendExecuteAction(action, getState)
        }
    }

    open suspend fun suspendExecuteIntent(intent: Intent, getState: () -> State) {}
    open suspend fun suspendExecuteAction(action: Action, getState: () -> State) {}
}