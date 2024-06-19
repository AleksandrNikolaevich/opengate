package ru.kode.tools.opengate.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface OpenGateResponse {
    @Serializable
    data class Success(
        val id: String,
        val state: Int
    )

    @Serializable
    data class Error(
        val error: Body
    ) {
        @Serializable
        data class Body(
            val code: Int,
            val msg: String
        )
    }
}