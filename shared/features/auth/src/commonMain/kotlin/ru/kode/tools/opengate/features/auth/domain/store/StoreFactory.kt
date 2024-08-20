package ru.kode.tools.opengate.features.auth.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.kode.tools.opengate.features.auth.domain.Repository

internal class StoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: Repository
) {
    fun create(): AuthStore = object :
        AuthStore,
        Store<AuthStore.Intent, AuthStore.State, Nothing> by storeFactory.create(
            name = AuthStore::class.simpleName,
            initialState = AuthStore.State(),
            bootstrapper = null,
            executorFactory = {
                Executor(
                    repository = repository,
                )
            },
            reducer = Reducer(),
        ) {}

    sealed interface Message {
        object SetLoading : Message
        class SetLoggedIn(val state: Boolean) : Message
        class SetError(val message: String) : Message
    }
}