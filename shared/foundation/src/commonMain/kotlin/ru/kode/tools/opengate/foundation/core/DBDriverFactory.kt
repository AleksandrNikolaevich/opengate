package ru.kode.tools.opengate.foundation.core

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

expect class DBDriverFactory {
    fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>): SqlDriver
}
