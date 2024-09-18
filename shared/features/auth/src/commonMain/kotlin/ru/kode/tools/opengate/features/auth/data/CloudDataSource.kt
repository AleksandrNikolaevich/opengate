package ru.kode.tools.opengate.features.auth.data

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import ru.kode.tools.opengate.foundation.core.Response
import ru.kode.tools.opengate.foundation.data.Api
import ru.kode.tools.opengate.foundation.data.GatesListResponse
import ru.kode.tools.opengate.features.auth.domain.Credentials

internal class CloudDataSource(
    private val api: Api,
)  {
    suspend fun signIn(login: String, password: String): Response<Boolean> {
        try {
            val response = api.signIn(login, password)

            when (response.status) {
                HttpStatusCode.OK -> {
                    return try {
                        response.body<List<GatesListResponse.Success>>()

                        authenticate(Credentials(login = login, password = password))

                        Response.Success(true)
                    } catch (e: JsonConvertException) {
                        val data = response.body<GatesListResponse.Error>()

                        Response.Failed(Error(data.error.msg))
                    }
                }
            }
        } catch (_: Throwable) {}

        return  Response.Failed(Error("Some problem. Try later..."))
    }

    fun authenticate(credentials: Credentials) = api.authenticate(credentials.login, credentials.password)

    fun logout() = api.logout()
}