package ru.kode.tools.opengate.modules.gates.domain

import ru.kode.tools.opengate.core.Response

internal interface Repository {
    fun getGates(): List<Gate>
    suspend fun openGate(id: String, key: String): Response<Boolean>
}