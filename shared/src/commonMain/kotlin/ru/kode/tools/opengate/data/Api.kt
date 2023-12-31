package ru.kode.tools.opengate.data

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters

internal class Api(
    private val httpClient: HttpClient
) {
    suspend fun signIn(login: String, password: String): HttpResponse {
        return httpClient.post("https://security.eldes.lt/api1?gatelogin=1") {
            setBody(
                FormDataContent(Parameters.build {
                    append("login", login)
                    append("psw", password)
                })
            )
        }
    }
}