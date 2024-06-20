package ru.kode.tools.opengate.data

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import ru.kode.tools.opengate.domain.AuthError

class Api(
    private val httpClient: HttpClient
) {
    private var login: String? = null
    private var password: String? = null

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

    suspend fun getGates(): HttpResponse {
        if (login == null || password == null) {
            throw AuthError()
        }
        return signIn(login!!, password!!)
    }

    suspend fun openGate(id: String, key: String): HttpResponse {
        if (login == null || password == null) {
            throw AuthError()
        }

        return httpClient.post("https://security.eldes.lt/api1") {
            setBody(
                FormDataContent(Parameters.build {
                    append("json", "{\"vars\":{\"OPN\":\"1;$login\"}}")
                })
            )

            url {
                parameters.append("update", "device")
                parameters.append("id", id)
                parameters.append("key", key)
            }
        }
    }

    fun authenticate(login: String, password: String) {
        this.login = login
        this.password = password
    }

    fun logout() {
        login = null
        password = null
    }
}