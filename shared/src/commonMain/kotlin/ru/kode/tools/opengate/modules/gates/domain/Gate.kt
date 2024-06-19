package ru.kode.tools.opengate.modules.gates.domain

data class Gate(
    val id: String,
    val key: String,
    val name: String,
    val isAvailable: Boolean,
)
