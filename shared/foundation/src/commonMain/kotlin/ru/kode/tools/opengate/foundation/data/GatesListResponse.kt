package ru.kode.tools.opengate.foundation.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface GatesListResponse {
    @Serializable
    data class Success(
        val id: String,
        val key: String,
        val name: String,
        @SerialName("user_id")
        val userId: String,
        @SerialName("owner_id")
        val owner_id: String,
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