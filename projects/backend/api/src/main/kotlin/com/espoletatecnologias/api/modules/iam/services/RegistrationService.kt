package com.espoletatecnologias.api.modules.iam.services

import io.ktor.server.freemarker.*

class RegistrationService(private val kratosClient: KratosClient) {
    suspend fun registration(flowId: String): FreeMarkerContent {
        val inputResponse = kratosClient.getKratosRegistration(flowId)

        return FreeMarkerContent(
            "registration.ftl", mapOf(
                "flow" to flowId,
                "response" to inputResponse.toString()
            )
        )
    }
}
