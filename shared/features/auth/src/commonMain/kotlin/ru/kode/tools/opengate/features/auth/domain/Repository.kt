package ru.kode.tools.opengate.features.auth.domain

import ru.kode.tools.opengate.foundation.core.Response

internal interface Repository {
    suspend fun signIn(login: String, password: String): Response<Boolean>

    fun checkLogin(): Boolean

    fun logout()
}