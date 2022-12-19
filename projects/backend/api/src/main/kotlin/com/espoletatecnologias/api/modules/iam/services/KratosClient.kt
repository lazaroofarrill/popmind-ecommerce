package com.espoletatecnologias.api.modules.iam.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

class KratosClient(private val httpClient: HttpClient) {
    private val kratosUrl = Url("http://127.0.0.1:4433")

    suspend fun getKratosRegistration(flowId: String): KratosRegistrationResponse {
        val registrationApiUrl = Url("${kratosUrl.fullPath}/registration/api")
        val requestResult = httpClient.get {
            url(registrationApiUrl)
            parameter("flow", flowId)
        }

        return requestResult.body()
    }

    @Serializable
    data class KratosRegistrationResponse(
        val id: String,
        val type: String,
        val messages: List<KratosMessage>?,
        val methods: Map<String, KratosMethod>
    ) {
        @Serializable
        data class KratosMethod(
            val method: String,
            val config: KratosMethodConfig
        )

        @Serializable
        data class KratosMethodConfig(
            val action: String,
            val method: String,
            val fields: List<KratosField>
        )

        @Serializable
        data class KratosField(
            val name: String,
            val type: String,
            val required: Boolean = false,
            val value: String?,
            val messages: List<KratosMessage>?
        )

        @Serializable
        data class KratosMessage(
            val id: String,
            val text: String,
            val type: String
        )
    }
}
