package ru.kode.tools.opengate.di

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import org.koin.dsl.module
import ru.kode.tools.opengate.core.HttpClientFactory

internal var commonModule = module {
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