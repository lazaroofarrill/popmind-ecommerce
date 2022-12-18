package com.espoletatecnologias.api.modules.iam.controller

import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.modules.iam.services.IAMService
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/iam")
class IAMRoutes {
    @Serializable
    @Resource("/login")
    class Login(val parent: IAMRoutes = IAMRoutes())

    @Serializable
    @Resource("/logout")
    class Logout(val parent: IAMRoutes = IAMRoutes())
}

class IAMController(private val service: IAMService) : Controller {
    private fun Route.login() {
        get<IAMRoutes.Login> {
            call.respondText(service.login())
        }
    }

    private fun Route.logout() {
        get<IAMRoutes.Logout> {
            call.respondText(service.logout())
        }
    }

    override val router: Route.() -> Unit = {
        login()
        logout()
    }
}
