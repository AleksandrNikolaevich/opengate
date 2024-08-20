package ru.kode.tools.opengate.features.auth.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import io.github.aakira.napier.Napier
import org.koin.dsl.module
import ru.kode.tools.opengate.features.auth.data.CloudDataSource
import ru.kode.tools.opengate.features.auth.data.RepositoryImpl
import ru.kode.tools.opengate.features.auth.data.SecurityStorageSource
import ru.kode.tools.opengate.features.auth.domain.store.AuthStore
import ru.kode.tools.opengate.features.auth.domain.Repository

val authModule = module {
    single<AuthStore>() {
        ru.kode.tools.opengate.features.auth.domain.store.StoreFactory(
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