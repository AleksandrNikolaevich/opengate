package ru.kode.tools.opengate.modules.auth.domain

internal data class Barrier(
    val id: String,
    val key: String,
    val name: String,
    val isAvailable: Boolean,
)
