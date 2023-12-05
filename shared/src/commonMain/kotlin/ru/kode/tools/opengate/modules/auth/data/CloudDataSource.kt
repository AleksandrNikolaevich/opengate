package ru.kode.tools.opengate.modules.auth.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.serialization.JsonConvertException
import ru.kode.tools.opengate.core.Mapper
import ru.kode.tools.opengate.core.Response
import ru.kode.tools.opengate.data.Api
import ru.kode.tools.opengate.data.SignInResponse
import ru.kode.tools.opengate.modules.auth.domain.Barrier

internal class CloudDataSource(
    private val mapper: Mapper<SignInResponse.Success, Barrier>,
    private val api: Api
)  {
    suspend fun signIn(login: String, password: String): Response<List<Barrier>> {
        val response = api.signIn(login, password)

        when (response.status) {
            HttpStatusCode.OK -> {
                return try {
                    val data = response.body<List<SignInResponse.Success>>()

                    Response.Success(
                        data = mapper.map(data)
                    )
                } catch (e: JsonConvertException) {
                    val data = response.body<SignInResponse.Error>()

                    Response.Failed(Error(data.error.msg))
                }

            }
        }

        return Response.Failed(
            IllegalStateException("Failed to auth")
        )
    }
}