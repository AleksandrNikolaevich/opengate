package ru.kode.tools.opengate.features.gates.data

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import ru.kode.tools.opengate.foundation.core.Mapper
import ru.kode.tools.opengate.foundation.core.Response
import ru.kode.tools.opengate.foundation.data.Api
import ru.kode.tools.opengate.foundation.data.OpenGateResponse
import ru.kode.tools.opengate.foundation.data.GatesListResponse
import ru.kode.tools.opengate.features.gates.domain.Gate

internal class CloudDataSource(
    private val mapper: Mapper<GatesListResponse.Success, Gate>,
    private val api: Api
)  {
    suspend fun openGate(id: String, key: String): Response<Boolean> {
        try {
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
                    }
                }
            }
        } catch (_: Throwable) {}

        return Response.Failed(Error("Some problem. Try later..."))
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