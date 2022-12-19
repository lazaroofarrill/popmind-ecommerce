package com.espoletatecnologias.api.modules.iam.services

import io.ktor.server.freemarker.*
import sh.ory.kratos.ApiClient

class RegistrationService {
    fun registration(flowId: String): FreeMarkerContent {
        val client = ApiClient()

        return FreeMarkerContent("registration.ftl", mapOf("flow" to flowId))
    }
}
