package ru.kode.tools.opengate.features.auth.domain.store

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.kode.tools.opengate.foundation.core.BaseExecutor
import ru.kode.tools.opengate.foundation.core.Response
import ru.kode.tools.opengate.features.auth.domain.Repository

internal class Executor(
    private val repository: Repository,
) : BaseExecutor<AuthStore.Intent, Nothing, AuthStore.State, StoreFactory.Message, Nothing>(
    mainContext = Dispatchers.Main
) {
    override suspend fun suspendExecuteIntent (
        intent: AuthStore.Intent,
        getState: () -> AuthStore.State,
    ) = when (intent) {
        is AuthStore.Intent.SignIn -> signIn(intent.login, intent.password)
        is AuthStore.Intent.Logout -> logout()
        is AuthStore.Intent.CheckLogin -> checkLogin()
    }

    private suspend fun signIn(login: String, password: String) {
        dispatch(StoreFactory.Message.SetLoading)

        val response = scope.async(Dispatchers.IO) {
            repository.signIn(login, password)
        }.await()

        when (response) {
//            is Response.Cached -> dispatch(StoreFactory.Message.SetData(response.data, true))
            is Response.Success -> dispatch(StoreFactory.Message.SetLoggedIn(true))
            is Response.Failed -> dispatch(
                StoreFactory.Message.SetError(
                    response.throwable.message ?: "Error"
                )
            )
            else -> dispatch(StoreFactory.Message.SetError("Unknown error"))
        }
    }

    private suspend fun checkLogin() {
        val isLoggedIn = scope.async(Dispatchers.IO) {
            repository.checkLogin()
        }.await()

        dispatch(StoreFactory.Message.SetLoggedIn(isLoggedIn))
    }

    private fun logout() {
        scope.launch(Dispatchers.IO) {
            repository.logout()
        }

        dispatch(StoreFactory.Message.SetLoggedIn(false))
    }
}