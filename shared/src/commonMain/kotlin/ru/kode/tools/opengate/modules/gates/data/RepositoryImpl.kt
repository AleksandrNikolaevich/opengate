package ru.kode.tools.opengate.modules.gates.data

import ru.kode.tools.opengate.core.Response
import ru.kode.tools.opengate.modules.gates.domain.Gate
import ru.kode.tools.opengate.modules.gates.domain.Repository

internal class RepositoryImpl(
    private val dbDataSource: DBDataSource,
    private val cloudDataSource: CloudDataSource,
    private val securityStorageSource: SecurityStorageSource
) : Repository {
    override fun getGates(): List<Gate> {
        return dbDataSource.getGates()
    }

    override suspend fun openGate(id: String, key: String): Response<Boolean> {
        // TODO: need logout
        val login = securityStorageSource.getLogin() ?: return Response.Failed(Error("401"))
        return cloudDataSource.openGate(login, id, key)
    }
}