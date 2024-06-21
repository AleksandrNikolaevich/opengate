package ru.kode.tools.opengate.modules.auth.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import io.github.aakira.napier.Napier
import org.koin.dsl.module
import ru.kode.tools.opengate.modules.auth.data.CloudDataSource
import ru.kode.tools.opengate.modules.auth.data.RepositoryImpl
import ru.kode.tools.opengate.modules.auth.data.SecurityStorageSource
import ru.kode.tools.opengate.modules.auth.presentation.AuthStore
import ru.kode.tools.opengate.modules.auth.domain.Repository

internal val authModule = module {
    single<AuthStore>() {
        ru.kode.tools.opengate.modules.auth.presentation.StoreFactory(
            storeFactory = get(),
            repository = get(),
        ).create()
    }

    single<StoreFactory> {
        val logger = object : com.arkivanov.mvikotlin.logging.logger.Logger {
            override fun log(text: String) {
                Napier.v(text)
            }
        }
        LoggingStoreFactory(DefaultStoreFactory(), logger = logger)
    }

    single<Repository>() {
        RepositoryImpl(
            cloudDataSource = get(),
            securityStorage = get()
        )
    }

    single<CloudDataSource>() {
        CloudDataSource(
            api = get(),
        )
    }

    single<SecurityStorageSource> {
        SecurityStorageSource(
            securityStorageFactory = get()
        )
    }
}