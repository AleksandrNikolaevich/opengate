package ru.kode.tools.opengate.foundation.core

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import ru.kode.tools.opengate.Database

actual class DBDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Database.Schema, "main.db")
    }
}