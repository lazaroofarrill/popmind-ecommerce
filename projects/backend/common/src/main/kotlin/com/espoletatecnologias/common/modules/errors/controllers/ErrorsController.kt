package com.espoletatecnologias.common.modules.errors.controllers

import com.espoletatecnologias.common.framework.arch.Controller
import com.espoletatecnologias.common.framework.types.Router
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

class ErrorsController : Controller {
    override val router: Router = {
        get<Routes> {
            call.respond(
                FreeMarkerContent(
                    "error.ftl",
                    mapOf("errorId" to it.id)
                )
            )
        }
    }

    @Serializable
    @Resource("/error")
    private class Routes(val id: String)
}
