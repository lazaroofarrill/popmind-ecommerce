package com.espoletatecnologias.api.modules.iam.services

import io.ktor.server.freemarker.*

class AuthService(private val kratosClient: KratosClient) {
    suspend fun registration(
        flowId: String,
        csrfToken: String
    ): FreeMarkerContent {
        val inputForm =
            kratosClient.getKratosRegistration(flowId, csrfToken)

        return FreeMarkerContent(
            "registration.ftl", mapOf(
                "flow" to flowId,
                "response" to when (inputForm) {
                    is KratosClient.KratosResponse.SelfServiceApiResponse -> {
                        inputForm
                    }

                    else -> {
                        throw Error("Error parsing the request")
                    }
                }
            )
        )
    }

    suspend fun login(flowId: String, csrfToken: String): FreeMarkerContent {
        val inputForm = kratosClient.getKratosLogin(flowId, csrfToken)

        return FreeMarkerContent(
            "login.ftl",
            mapOf<String, Any>(
                "flow" to flowId,
                "response" to inputForm
            )
        )
    }
}
