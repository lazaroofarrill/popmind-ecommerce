package com.espoletatecnologias.api.framework.common.utils

import com.espoletatecnologias.api.clean.crud.FindManyResponse
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

fun <T> PipelineContext<*, ApplicationCall>.setFindMeta(
    findManyResponse: FindManyResponse<T>
) {
    call.response.header("meta-limit", findManyResponse.limit)
    call.response.header("meta-offset", findManyResponse.offset)
    call.response.header("meta-count", findManyResponse.count)
    call.response.header("meta-total", findManyResponse.total)
}
