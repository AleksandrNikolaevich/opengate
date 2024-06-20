package ru.kode.tools.opengate.modules.auth.domain

import ru.kode.tools.opengate.core.Response
import ru.kode.tools.opengate.modules.auth.data.SecurityStorageSource

internal interface Repository {
    suspend fun signIn(login: String, password: String): Response<Boolean>

    fun checkLogin(): Boolean

    fun logout()
}