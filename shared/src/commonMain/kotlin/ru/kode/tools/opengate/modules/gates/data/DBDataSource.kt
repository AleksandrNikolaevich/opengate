package ru.kode.tools.opengate.modules.gates.data

import ru.kode.tools.opengate.core.DBDriverFactory
import ru.kode.tools.opengate.core.createDatabase
import ru.kode.tools.opengate.modules.gates.domain.Gate

internal class DBDataSource(
    private val driverFactory: DBDriverFactory,
) {
    private val database = createDatabase(driverFactory)
    private val dbQuery = database.databaseQueries

    internal fun getGates(): List<Gate> {
        return dbQuery.getBarriers(::mapBarrierSelecting).executeAsList()
    }

    private fun mapBarrierSelecting(
        id: String,
        key: String,
        name: String,
        state: Long
    ): Gate {
        return Gate(
            id = id,
            key = key,
            name = name,
            isAvailable = state == 1L
        )
    }
}