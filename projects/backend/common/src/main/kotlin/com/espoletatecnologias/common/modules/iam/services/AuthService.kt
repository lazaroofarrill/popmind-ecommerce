package com.espoletatecnologias.common.modules.iam.services

import io.ktor.server.freemarker.*

class AuthService(private val kratosClient: KratosClient) {
    suspend fun registration(
        flowId: String,
        cookies: List<String>
    ): FreeMarkerContent {
        val inputForm =
            kratosClient.getKratosRegistration(flowId, cookies)

        return FreeMarkerContent(
            "registration.ftl", mapOf(
                "flow" to flowId,
                "response" to when (inputForm) {
                    is KratosClient.KratosResponse.KratosSelfService -> {
                        inputForm
                    }

                    else -> {
                        throw Error("Error parsing the request")
                    }
                }
            )
        )
    }

    suspend fun login(
        flowId: String,
        cookies: List<String>
    ): FreeMarkerContent {
        val inputForm = kratosClient.getKratosLogin(flowId, cookies)

        return FreeMarkerContent(
            "login.ftl",
            mapOf<String, Any>(
                "flow" to flowId,
                "response" to inputForm
            )
        )
    }
}
