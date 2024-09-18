package ru.kode.tools.opengate.features.gates.data

import ru.kode.tools.opengate.foundation.core.Response
import ru.kode.tools.opengate.features.gates.domain.Gate
import ru.kode.tools.opengate.features.gates.domain.Repository

internal class RepositoryImpl(
    private val dbDataSource: DBDataSource,
    private val cloudDataSource: CloudDataSource,
) : Repository {
    override suspend fun getGates(force: Boolean): Response<List<Gate>> {
        val cachedData = dbDataSource.getGates()

        return if (cachedData.isNotEmpty() && !force)
            Response.Cached(data = cachedData)
        else
            getGatesWithPersistData(cachedData)
    }

    override suspend fun openGate(id: String, key: String): Response<Boolean> {
        return cloudDataSource.openGate(id, key)
    }

    override suspend fun setShortName(id: String, shortName: String?) {
        val gate = dbDataSource.getGates().find { gate -> gate.id == id } ?: return
        dbDataSource.addOrUpdateGate(gate.copy(shortName = shortName))
    }

    private suspend fun getGatesWithPersistData(cachedData:  List<Gate>): Response<List<Gate>> {
        val remoteData = cloudDataSource.getGates()

        if (remoteData is Response.Success) {
            val listOfGates = remoteData.data.sortedBy(::sortById).map { item ->
                val oldData = cachedData.find { gate -> gate.id == item.id }
                item.copy(shortName = oldData?.shortName)
            }


            dbDataSource.addGates(listOfGates)

            return Response.Success(listOfGates)
        }

        return remoteData
    }

    override fun clearData() {
        dbDataSource.clearData()
    }
}