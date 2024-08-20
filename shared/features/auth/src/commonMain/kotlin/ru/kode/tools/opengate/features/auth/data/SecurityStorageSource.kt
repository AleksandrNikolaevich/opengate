package ru.kode.tools.opengate.features.auth.data

import ru.kode.tools.opengate.foundation.core.SecurityStorage
import ru.kode.tools.opengate.foundation.core.SecurityStorageFactory
import ru.kode.tools.opengate.features.auth.domain.Credentials

class SecurityStorageSource(private val securityStorageFactory: SecurityStorageFactory) {
    private val storage: SecurityStorage = securityStorageFactory.create()
    fun saveCredentials(login: String, password: String): Boolean {
        val result = storage.set(LOGIN_KEY, login) && storage.set(PASSWORD_KEY, password)

        if (!result) {
            clearCredentials()
        }

        return result
    }

    fun getCredentials(): Credentials? {
        val login = storage.get(LOGIN_KEY) ?: ""
        val password = storage.get(PASSWORD_KEY) ?: ""

        if (login.isEmpty() || password.isEmpty()) {
            return null
        }

        return Credentials(login, password)
    }

    fun clearCredentials() {
        storage.remove(LOGIN_KEY)
        storage.remove(PASSWORD_KEY)
    }

    companion object {
        private const val LOGIN_KEY = "login"
        private const val PASSWORD_KEY = "password"
    }
}