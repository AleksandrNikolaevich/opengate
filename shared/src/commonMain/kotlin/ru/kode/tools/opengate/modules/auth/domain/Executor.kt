package ru.kode.tools.opengate.modules.auth.domain

import kotlinx.coroutines.Dispatchers
import ru.kode.tools.opengate.core.BaseExecutor
import ru.kode.tools.opengate.core.Response

internal class Executor(
    private val repository: Repository,
) : BaseExecutor<AuthStore.Intent, Nothing, AuthStore.State, StoreFactory.Message, Nothing>(
    mainContext = Dispatchers.Main
) {
    override suspend fun suspendExecuteIntent (
        intent: AuthStore. Intent,
        getState: () -> AuthStore.State,
    ) = when (intent) {
        is AuthStore.Intent.SignIn -> signIn(intent.login, intent.password)
    }

    private suspend fun signIn(login: String, password: String) {
        dispatch(StoreFactory.Message.SetLoading)

        when (val response = repository.signIn(login, password)) {
//            is Response.Cached -> dispatch(StoreFactory.Message.SetData(response.data, true))
            is Response.Success -> dispatch(StoreFactory.Message.SetLoggedIn)
            is Response.Failed -> dispatch(StoreFactory.Message.SetError(response.throwable.message ?: "Error"))
            else -> dispatch(StoreFactory.Message.SetError("Unknown error"))
        }
    }
}