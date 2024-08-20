package ru.kode.tools.opengate.features.gates.data

import ru.kode.tools.opengate.foundation.core.DBDriverFactory
import ru.kode.tools.opengate.foundation.core.createDatabase
import ru.kode.tools.opengate.features.gates.domain.Gate

internal class DBDataSource(
    private val driverFactory: DBDriverFactory,
) {
    private val database = createDatabase(driverFactory)
    private val dbQuery = database.databaseQueries

    internal fun getGates(): List<Gate> {
        return dbQuery.getBarriers(::mapBarrierSelecting).executeAsList()
    }

    internal fun addGates(gates: List<Gate>) {
        dbQuery.transaction {
            gates.forEach(::addGate)
        }
    }

    internal fun addGate(gate: Gate) {
        dbQuery.updateBarrierWithId(gate.id, gate.key, gate.name, if (gate.isAvailable) 1 else 0)
    }

    internal fun clearData() {
        dbQuery.removeBarriers()
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