package ru.kode.tools.opengate.modules.gates.domain

data class OpenGateState(
    val id: String,
    var state: State,
) {
    enum class State {
        PENDING, OPENING, OPENED, ERROR
    }
}