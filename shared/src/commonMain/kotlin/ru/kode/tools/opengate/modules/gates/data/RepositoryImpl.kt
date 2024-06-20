package ru.kode.tools.opengate.modules.gates.data

import ru.kode.tools.opengate.core.Response
import ru.kode.tools.opengate.modules.gates.domain.Gate
import ru.kode.tools.opengate.modules.gates.domain.Repository

internal class RepositoryImpl(
    private val dbDataSource: DBDataSource,
    private val cloudDataSource: CloudDataSource,
) : Repository {
    override suspend fun getGates(force: Boolean): Response<List<Gate>> {
        if (force) {
            dbDataSource.clearData()
            return getGatesWithPersistData()
        }

        val cachedData = dbDataSource.getGates()

        return if (cachedData.isNotEmpty())
            Response.Cached(data = cachedData)
        else
            getGatesWithPersistData()
    }

    override suspend fun openGate(id: String, key: String): Response<Boolean> {
        return cloudDataSource.openGate(id, key)
    }

    private suspend fun getGatesWithPersistData(): Response<List<Gate>> {
        val remoteData = cloudDataSource.getGates()

        if (remoteData is Response.Success) {
            dbDataSource.addGates(remoteData.data)
        }

        return remoteData
    }

    override fun clearData() {
        dbDataSource.clearData()
    }
}