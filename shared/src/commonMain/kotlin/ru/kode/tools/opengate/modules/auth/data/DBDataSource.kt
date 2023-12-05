package ru.kode.tools.opengate.modules.auth.data

import ru.kode.tools.opengate.core.DBDriverFactory
import ru.kode.tools.opengate.core.createDatabase
import ru.kode.tools.opengate.modules.auth.domain.Barrier

internal class DBDataSource(
    private val driverFactory: DBDriverFactory
) {
    private val database = createDatabase(driverFactory)
    private val dbQuery = database.databaseQueries

    internal fun addBarriers(barriers: List<Barrier>) {
        dbQuery.transaction {
            barriers.forEach(::addBarrier)
        }
    }

    internal fun addBarrier(barrier: Barrier) {
        dbQuery.updateBarrierWithId(barrier.id, barrier.key, barrier.name, if (barrier.isAvailable) 1 else 0)
    }
}