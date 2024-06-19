package ru.kode.tools.opengate.modules.gates.data

import io.github.aakira.napier.Napier
import ru.kode.tools.opengate.core.SecurityStorage
import ru.kode.tools.opengate.core.SecurityStorageFactory

class SecurityStorageSource(private val securityStorageFactory: SecurityStorageFactory) {
    private val storage: SecurityStorage = securityStorageFactory.create()

    fun getLogin(): String? {
        return storage.get(LOGIN_KEY)
    }

    companion object {
        private const val LOGIN_KEY = "login"
    }
}