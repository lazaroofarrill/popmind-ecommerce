package com.espoletatecnologias.api.modules.iam.services

import io.ktor.server.freemarker.*

class RegistrationService(private val kratosClient: KratosClient) {
    suspend fun registration(
        flowId: String,
        csrfToken: Pair<String, String>
    ): FreeMarkerContent {
        val inputResponse =
            kratosClient.getKratosRegistration(flowId, csrfToken)

        return FreeMarkerContent(
            "registration.ftl", mapOf(
                "flow" to flowId,
                "response" to when (inputResponse) {
                    is KratosClient.KratosRegistrationResponse.SelfServiceApiResponse -> {
                        inputResponse
                    }

                    else -> {
                        throw Error("Error parsing the request")
                    }
                }
            )
        )
    }
}
