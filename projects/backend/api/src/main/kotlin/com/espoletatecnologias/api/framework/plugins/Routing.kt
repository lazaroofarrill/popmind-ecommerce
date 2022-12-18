package com.espoletatecnologias.api.framework.plugins

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(rootModule: ApplicationModule) {
    install(Routing) {
        get {
            call.respondText("Hello World!")
        }

        bootstrapModule(rootModule)
    }
}

fun Routing.bootstrapModule(module: ApplicationModule) {
    module.controllers().forEach { controller ->
        controller.router(this)
    }
    module.children.forEach { child ->
        bootstrapModule(child)
    }
}
