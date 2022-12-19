package com.espoletatecnologias.api.framework.plugins

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(rootModule: ApplicationModule) {
    install(Routing) {
        get("/") {
            call.respondRedirect("/iam")
        }

        bootstrapRoutes(rootModule)

        static("/") {
            resources("public")
        }
    }
}

fun Routing.bootstrapRoutes(module: ApplicationModule) {
    module.controllers().forEach { controller ->
        controller.router(this)
    }
    module.children.forEach { child ->
        bootstrapRoutes(child)
    }
}
