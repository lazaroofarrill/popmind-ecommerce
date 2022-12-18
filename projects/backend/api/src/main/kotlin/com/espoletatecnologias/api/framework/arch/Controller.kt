package com.espoletatecnologias.api.framework.arch

import io.ktor.server.routing.*

interface Controller {
    val router: Route.() -> Unit
}
