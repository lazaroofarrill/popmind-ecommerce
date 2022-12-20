package com.espoletatecnologias.api.modules.iam.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class KratosClient(private val httpClient: HttpClient) {
    private val kratosUrl = "http://127.0.0.1:4433"

    suspend fun getKratosRegistration(
        flowId: String,
        csrfToken: Pair<String, String>
    ): KratosRegistrationResponse {
        val registrationApiUrl = "$kratosUrl/self-service/registration/flows"

        val requestResult = httpClient.get(registrationApiUrl) {
            url {
                parameters.append("id", flowId)
                headers.append(
                    "Cookie",
                    "${csrfToken.first}=${csrfToken.second}"
                )
            }
        }


        return if (requestResult.status == HttpStatusCode.OK) {
            requestResult.body<KratosRegistrationResponse.SelfServiceApiResponse>()
        } else {
            requestResult.body<KratosRegistrationResponse.KratosFlowErrorResponse>()
            throw Error("Error getting identity schema")
        }

    }

    sealed class KratosRegistrationResponse {

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
        ) : KratosRegistrationResponse() {
            @Serializable
            data class UI(
                val action: String,
                val method: String,
                val nodes: List<Node>,
                val messages: List<Node.Message> = emptyList()
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
                        val value: String? = null
                    )

                    @Serializable
                    data class Message(
                        val id: String,
                        val text: String,
                        val type: String,
                        val context: Meta.Context? = null
                    )

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
                        data class Context(
                            val label: String? = null,

                            @SerialName("expired_at")
                            val expiredAt: String? = null,

                            val property: String? = null,

                            val reason: String? = null
                        )
                    }
                }
            }
        }

        @Serializable
        data class KratosFlowErrorResponse(val error: ErrorData) :
            KratosRegistrationResponse() {

            @Serializable
            data class ErrorData(
                val id: String,
                val code: Int,
                val status: String,
                val reason: String,
                val details: ErrorDetails,
                val message: String
            ) {
                @Suppress("unused")
                @Serializable
                class ErrorDetails(
                    val docs: String? = null,

                    val hint: String? = null,

                    @SerialName("reject_reason")
                    val rejectReason: String? = null,

                    @SerialName("redirect_to")
                    val redirectTo: String? = null,

                    @SerialName("return_to")
                    val returnTo: String? = null
                )
            }
        }
    }
}
