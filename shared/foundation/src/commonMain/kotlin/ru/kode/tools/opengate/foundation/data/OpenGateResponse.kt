package ru.kode.tools.opengate.foundation.data

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