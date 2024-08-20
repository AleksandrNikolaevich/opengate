package ru.kode.tools.opengate.features.auth.data

import ru.kode.tools.opengate.foundation.core.Response
import ru.kode.tools.opengate.features.auth.domain.Credentials
import ru.kode.tools.opengate.features.auth.domain.Repository

internal class RepositoryImpl(
    private val cloudDataSource: CloudDataSource,
    private val securityStorage: SecurityStorageSource
) : Repository {
    override suspend fun signIn(login: String, password: String): Response<Boolean> {
        val response = cloudDataSource.signIn(login, password)

        if (response is Response.Success) {
            securityStorage.saveCredentials(login, password)
        }

        return response
    }

    override fun checkLogin(): Boolean {
        val credentials = securityStorage.getCredentials()

        if (credentials != null) {
            authenticate(credentials)
            return true
        }

        return false
    }

    override fun logout() {
        securityStorage.clearCredentials()
        cloudDataSource.logout()
    }

    private fun authenticate(credentials: Credentials) {
        cloudDataSource.authenticate(credentials)
    }
}