package ru.kode.tools.opengate.modules.gates.domain

import ru.kode.tools.opengate.core.Response

internal interface Repository {
    suspend fun getGates(force: Boolean): Response<List<Gate>>
    suspend fun openGate(id: String, key: String): Response<Boolean>
    fun clearData()
}