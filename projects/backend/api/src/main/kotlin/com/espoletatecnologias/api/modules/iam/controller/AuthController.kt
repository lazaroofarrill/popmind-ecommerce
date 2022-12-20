package com.espoletatecnologias.api.modules.iam.controller

import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.framework.types.Router
import com.espoletatecnologias.api.modules.iam.services.IAMService
import com.espoletatecnologias.api.modules.iam.services.RegistrationService
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import kotlinx.serialization.Serializable



class AuthController(
    private val iamService: IAMService,
    private val registrationService: RegistrationService
) : Controller {

    private fun Route.index() {
        get<IAMRoutes> {
            call.respond(
                FreeMarkerContent(
                    "index.ftl",
                    null
                )
            )
        }
    }

    private fun Route.login() {
        get<IAMRoutes.Login> {
            call.respond(
                FreeMarkerContent(
                    "login.ftl",
                    mapOf("name" to "lore")
                )
            )
        }
    }

    private fun Route.logout() {
        get<IAMRoutes.Logout> {
            call.respondText(iamService.logout())
        }
    }

    private fun Route.registration() {
        get<AuthRoutes.Registration> { registration ->
            val csrfToken = call.request.cookies.rawCookies.toList().find {
                it.first.contains("csrf_token")
            } ?: throw Error("csrf token not present")

            call.respond(
                registrationService.registration(
                    registration.flow,
                    csrfToken
                )
            )
        }
    }

    override val router: Router = {
        index()
        login()
        logout()
        registration()
    }

    @Suppress("unused")
    @Serializable
    @Resource("/iam")
    private class IAMRoutes {
        @Serializable
        @Resource("/login")
        class Login(val parent: IAMRoutes = IAMRoutes())

        @Serializable
        @Resource("/logout")
        class Logout(val parent: IAMRoutes = IAMRoutes())
    }

    @Suppress("unused")
    @Serializable
    @Resource("/auth")
    private class AuthRoutes {
        @Serializable
        @Resource("registration")
        class Registration(
            val parent: AuthRoutes = AuthRoutes(),
            val flow: String
        )
    }
}
