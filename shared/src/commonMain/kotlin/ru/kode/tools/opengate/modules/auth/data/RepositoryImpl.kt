package ru.kode.tools.opengate.modules.auth.data

import ru.kode.tools.opengate.core.Response
import ru.kode.tools.opengate.core.SecurityStorage
import ru.kode.tools.opengate.modules.auth.domain.Barrier
import ru.kode.tools.opengate.modules.auth.domain.Repository

internal class RepositoryImpl(
    private val cloudDataSource: CloudDataSource,
    private val dbDataSource: DBDataSource,
    private val securityStorage: SecurityStorageSource
) : Repository {
    override suspend fun signIn(login: String, password: String): Response<List<Barrier>> {
        val response = cloudDataSource.signIn(login, password)

        if (response is Response.Success) {
            dbDataSource.addBarriers(response.data)
            securityStorage.saveCredentials(login, password)
        }

        return response
    }

    override fun getCredentials(): SecurityStorageSource.Credentials? {
        return securityStorage.getCredentials()
    }

    override fun clearCredentials() {
        securityStorage.clearCredentials()
    }
}