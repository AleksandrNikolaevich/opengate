package ru.kode.tools.opengate.features.gates.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import io.github.aakira.napier.Napier
import org.koin.dsl.module
import ru.kode.tools.opengate.features.gates.GatesDatabase
import ru.kode.tools.opengate.features.gates.data.CloudDataSource
import ru.kode.tools.opengate.features.gates.data.DBDataSource
import ru.kode.tools.opengate.features.gates.data.RepositoryImpl
import ru.kode.tools.opengate.features.gates.data.mappers.GatesResponseMapper
import ru.kode.tools.opengate.features.gates.domain.store.GatesStore
import ru.kode.tools.opengate.features.gates.domain.Repository
import ru.kode.tools.opengate.foundation.core.DBDriverFactory

val gatesModule = module {
    single<GatesStore>() {
        ru.kode.tools.opengate.features.gates.domain.store.StoreFactory(
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
            dbDataSource = get(),
            cloudDataSource = get()
        )
    }

    single<CloudDataSource>() {
        CloudDataSource(
            api = get(),
            mapper = GatesResponseMapper()
        )
    }

    single<DBDataSource>() {
        DBDataSource(
            database = get()
        )
    }

    single<GatesDatabase>() {
        val driverFactory: DBDriverFactory = get()

        GatesDatabase(driverFactory.createDriver(GatesDatabase.Schema))
    }
}