package com.espoletatecnologias.api.modules.iam.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class KratosClient(private val httpClient: HttpClient) {
    private val kratosUrl = "http://127.0.0.1:4433"

    suspend fun getKratosRegistration(): SelfServiceApiResponse {
        val registrationApiUrl = Url("$kratosUrl/self-service/registration/api")
        println(registrationApiUrl)
        val requestResult = httpClient.get {
            url(registrationApiUrl)
        }

        return requestResult.body()
    }

    @Serializable
    data class SelfServiceApiResponse(
        val id: String,

        @SerialName("request_url")
        val requestUrl: String,

        val ui: UI,

        @SerialName("oauth2_login_challenge")
        val oauth2LoginChallenge: String?,

        val type: String,

        @SerialName("issued_at")
        val issuedAt: String,

        @SerialName("expires_at")
        val expiresAt: String
    ) {
        @Serializable
        data class UI(
            val action: String,
            val method: String,
            val nodes: List<Node>
        ) {

            @Serializable
            data class Node(
                val type: String,
                val group: String,
                val meta: Meta,
                val attributes: Attributes,
                val messages: List<Message>
            ) {
                @Serializable
                data class Attributes(
                    val autocomplete: String? = null,
                    val disabled: Boolean,
                    val name: String,
                    @SerialName("node_type") val nodeType: String,
                    val required: Boolean? = null,
                    val type: String,
                    val value: String = ""
                )

                @Serializable
                data class Message(val label: String)

                @Serializable
                data class Meta(
                    val label: Label? = null
                ) {
                    @Serializable
                    data class Label(
                        val id: Int,
                        val text: String,
                        val type: String,
                        val context: Context? = null
                    )

                    @Serializable
                    data class Context(val label: String? = null)
                }
            }
        }
    }
}
