package ru.kode.tools.opengate.foundation.core

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DBDriverFactory {
    actual fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>): SqlDriver {
        return NativeSqliteDriver(schema, "main.db")
    }
}