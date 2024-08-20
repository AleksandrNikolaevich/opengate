package ru.kode.tools.opengate.features.gates.domain

import ru.kode.tools.opengate.foundation.core.Response

internal interface Repository {
    suspend fun getGates(force: Boolean): Response<List<Gate>>
    suspend fun openGate(id: String, key: String): Response<Boolean>
    fun clearData()
}