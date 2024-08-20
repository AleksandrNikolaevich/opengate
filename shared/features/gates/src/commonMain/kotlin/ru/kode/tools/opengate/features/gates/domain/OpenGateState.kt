package ru.kode.tools.opengate.features.gates.domain

data class OpenGateState(
    val id: String,
    var state: State,
) {
    enum class State {
        PENDING, OPENING, OPENED, ERROR
    }
}