package ru.kode.tools.opengate.foundation.di

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import org.koin.dsl.module
import ru.kode.tools.opengate.foundation.core.HttpClientFactory
import ru.kode.tools.opengate.foundation.data.Api

internal var commonModule = module {
    single {
        Api(
            httpClient = get()
        )
    }

    single<HttpClient> {
        val logger = object : io.ktor.client.plugins.logging.Logger {
            override fun log(message: String) {
                Napier.v(message)
            }
        }
        HttpClientFactory().create(
            logger = logger,
        )
    }
}