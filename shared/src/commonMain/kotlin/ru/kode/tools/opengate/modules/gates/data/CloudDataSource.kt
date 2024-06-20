package ru.kode.tools.opengate.modules.gates.data

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import ru.kode.tools.opengate.core.Mapper
import ru.kode.tools.opengate.core.Response
import ru.kode.tools.opengate.data.Api
import ru.kode.tools.opengate.data.OpenGateResponse
import ru.kode.tools.opengate.data.GatesListResponse
import ru.kode.tools.opengate.modules.gates.domain.Gate

internal class CloudDataSource(
    private val mapper: Mapper<GatesListResponse.Success, Gate>,
    private val api: Api
)  {
    suspend fun openGate(id: String, key: String): Response<Boolean> {
        val response = api.openGate(id, key)

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
            IllegalStateException("Invalid response")
        )
    }

    suspend fun getGates(): Response<List<Gate>> {
        val response = api.getGates()

        when (response.status) {
            HttpStatusCode.OK -> {
                return try {
                    val data = response.body<List<GatesListResponse.Success>>()

                    Response.Success(
                        data = mapper.map(data)
                    )
                } catch (e: JsonConvertException) {
                    val data = response.body<GatesListResponse.Error>()

                    Response.Failed(Error(data.error.msg))
                } catch (e: RuntimeException) {
                    Response.Failed(Error("Some problem. Try later..."))
                }
            }
        }

        return Response.Failed(
            IllegalStateException("Invalid response")
        )
    }
}