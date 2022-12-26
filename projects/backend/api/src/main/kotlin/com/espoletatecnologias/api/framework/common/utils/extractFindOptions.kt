package com.espoletatecnologias.api.framework.common.utils

import com.espoletatecnologias.api.clean.crud.FilteringOrder
import com.espoletatecnologias.api.clean.crud.FindManyOptions
import io.ktor.server.application.*
import io.ktor.util.*
import io.ktor.util.pipeline.*

fun PipelineContext<*, ApplicationCall>.extractFindOptions(): FindManyOptions {
    var findManyOptions = FindManyOptions()

    call.parameters["limit"]?.toIntOrNull()?.let {
        findManyOptions = findManyOptions.copy(
            limit = it
        )
    }

    call.parameters["offset"]?.toLongOrNull()?.let {
        findManyOptions = findManyOptions.copy(
            offset = it
        )
    }

    call.parameters.filter { k, value ->
        k.startsWith("w_") && value.isNotBlank()
    }.forEach { s, strings ->
        findManyOptions =
            findManyOptions.copy(
                where = findManyOptions.where + mapOf(
                    s.substring(2) to if (strings.size == 1) strings[0] else strings
                )
            )
    }

    call.parameters.filter { k, value ->
        k.startsWith("o_") && value.isNotBlank()
    }.forEach { s, strings ->
        findManyOptions =
            findManyOptions.copy(
                order = findManyOptions.order + mapOf(
                    s.substring(2) to when (strings[0]) {
                        "ASC" -> FilteringOrder.ASC
                        "DESC" -> FilteringOrder.DESC
                        else -> throw Error("Invalid ordering value")
                    }
                )
            )
    }


    return findManyOptions
}
