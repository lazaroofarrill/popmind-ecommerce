package com.espoletatecnologias.api.modules.iam.services

import io.ktor.server.freemarker.*
import sh.ory.kratos.ApiClient

class RegistrationService {
    fun registration(flowId: String) {
        val client = ApiClient()

        FreeMarkerContent("registration.ftl", mapOf("flow" to flowId))
    }
}
