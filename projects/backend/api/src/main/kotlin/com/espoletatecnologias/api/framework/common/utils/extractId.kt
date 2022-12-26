package com.espoletatecnologias.api.framework.common.utils

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.util.pipeline.*
import java.util.*

@Suppress("unused")
fun PipelineContext<*, ApplicationCall>.extractStringId(): String {
    return call.parameters["id"]
        ?: throw BadRequestException("Incorrect id param")
}

fun PipelineContext<*, ApplicationCall>.extractUUID(): UUID {
    val idParam =
        call.parameters["id"] ?: throw BadRequestException("Incorrect id param")
    return UUID.fromString(idParam)
}
