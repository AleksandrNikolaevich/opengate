package ru.kode.tools.opengate.modules.auth.data

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import ru.kode.tools.opengate.core.Response
import ru.kode.tools.opengate.data.Api
import ru.kode.tools.opengate.data.GatesListResponse
import ru.kode.tools.opengate.modules.auth.domain.Credentials

internal class CloudDataSource(
    private val api: Api,
)  {
    suspend fun signIn(login: String, password: String): Response<Boolean> {
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
                } catch (e: RuntimeException) {
                    Response.Failed(Error("Some problem. Try later..."))
                }
            }
        }

        return Response.Failed(
            IllegalStateException("Failed to auth")
        )
    }

    fun authenticate(credentials: Credentials) = api.authenticate(credentials.login, credentials.password)

    fun logout() = api.logout()
}