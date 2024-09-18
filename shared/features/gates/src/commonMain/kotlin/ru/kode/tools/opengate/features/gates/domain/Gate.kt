package ru.kode.tools.opengate.features.gates.domain

data class Gate(
    val id: String,
    val key: String,
    val name: String,
    val isAvailable: Boolean,
    val state: OpenState,
    val shortName: String?
) {
    enum class OpenState {
        PENDING, OPENING, OPENED, ERROR
    }
}
