package com.espoletatecnologias.api.modules.iam.services

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

interface KratosClient {

    data class KratosRegistrationResponse(
        val id: String,
        val type: String,
        val messages: List<KratosMessage>?,
        val methods: Map<String, KratosMethod>
    ) {

        data class KratosMethod(
            val method: String,
            val config: KratosMethodConfig
        )

        data class KratosMethodConfig(
            val action: String,
            val method: String,
            val fields: List<KratosField>
        )

        data class KratosField(
            val name: String,
            val type: String,
            val required: Boolean = false,
            val value: String?,
            val messages: List<KratosMessage>?
        )

        data class KratosMessage(
            val id: String,
            val text: String,
            val type: String
        )
    }
}
