package com.espoletatecnologias.api

import com.espoletatecnologias.api.framework.plugins.*
import com.espoletatecnologias.api.modules.RootModule
import io.ktor.server.application.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(
        factory = io.ktor.server.netty.Netty,
        port = 8080,
        module = Application::module,
        watchPaths = listOf("classes")
    ).start(wait = true)
}

fun Application.module() {
    val rootModule = RootModule()
    configureKoin(rootModule)
    configureFreeMarker()
    configureResources()
    configureRouting(rootModule)
}

