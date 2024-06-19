package ru.kode.tools.opengate.modules.gates.data

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import ru.kode.tools.opengate.core.Response
import ru.kode.tools.opengate.data.Api
import ru.kode.tools.opengate.data.OpenGateResponse

internal class CloudDataSource(
    private val api: Api
)  {
    suspend fun openGate(login: String, id: String, key: String): Response<Boolean> {
        val response = api.openGate(login, id, key)

        when (response.status) {
            HttpStatusCode.OK -> {
                return try {
                    val data = response.body<OpenGateResponse.Success>()

                    Response.Success(
                        data = data.state == 1
                    )
                } catch (e: JsonConvertException) {
                    val data = response.body<OpenGateResponse.Error>()

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
}