package com.espoletatecnologias.api.modules.iam.controller

import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.framework.types.Router
import com.espoletatecnologias.api.modules.iam.services.AuthService
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import kotlinx.serialization.Serializable


class AuthController(
    private val authService: AuthService
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
        get<AuthRoutes.Login> { request ->
            val csrfToken = extractCsrfCookie()

            call.respond(
                authService.login(request.flow, csrfToken)
            )
        }
    }

    private fun Route.registration() {
        get<AuthRoutes.Registration> { registration ->
            val csrfToken = extractCsrfCookie()

            call.respond(
                authService.registration(
                    registration.flow,
                    csrfToken
                )
            )
        }
    }

    override val router: Router = {
        index()
        login()
        registration()
    }

    @Suppress("unused")
    @Serializable
    @Resource("/iam")
    private class IAMRoutes

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

        @Serializable
        @Resource("login")
        class Login(
            val parent: AuthRoutes = AuthRoutes(),
            val flow: String
        )
    }
}

private fun PipelineContext<Unit, ApplicationCall>.extractCsrfCookie(): String {
    val cookie = call.request.cookies.rawCookies.toList().find {
        it.first.contains("csrf_token")
    } ?: throw Error("csrf token not present")

    return "${cookie.first}=${cookie.second}"
}
