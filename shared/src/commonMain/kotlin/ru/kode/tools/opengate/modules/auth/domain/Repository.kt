package ru.kode.tools.opengate.modules.auth.domain

import ru.kode.tools.opengate.core.Response

internal interface Repository {
    suspend fun signIn(login: String, password: String): Response<List<Barrier>>
}