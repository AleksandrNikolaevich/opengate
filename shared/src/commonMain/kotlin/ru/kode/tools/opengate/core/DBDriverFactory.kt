package ru.kode.tools.opengate.core

import app.cash.sqldelight.db.SqlDriver
import ru.kode.tools.opengate.Database

expect class DBDriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DBDriverFactory): Database {
    val driver = driverFactory.createDriver()
    val database = Database(driver)

    // Do more work with the database

    return database
}