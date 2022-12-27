package com.espoletatecnologias.common.framework.plugins

import com.espoletatecnologias.common.framework.arch.ApplicationModule
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(modules: List<ApplicationModule>) {
    install(Routing) {
        get("/") {
            call.respondRedirect("/iam")
        }

        bootstrapRoutes(modules)

        static("/") {
            resources("public")
        }
    }
}


fun Routing.bootstrapRoutes(modules: List<ApplicationModule>) {
    modules.forEach {
        it.controllers().forEach { controller ->
            controller.router(this)
        }
    }
}
