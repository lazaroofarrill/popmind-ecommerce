package com.espoletatecnologias.common.modules.iam.controller

import com.espoletatecnologias.common.framework.arch.Controller
import com.espoletatecnologias.common.framework.types.Router
import com.espoletatecnologias.common.modules.iam.services.AuthService
import com.espoletatecnologias.common.modules.iam.services.KratosClient
import io.ktor.client.*
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
    private val authService: AuthService,
    private val kratosClient: KratosClient
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
            val csrfCookie = extractCsrfCookie()

            call.respond(
                authService.login(request.flow, listOf(csrfCookie))
            )
        }
    }

    private fun Route.registration() {
        get<AuthRoutes.Registration> { registration ->
            val csrfCookie = extractCsrfCookie()

            call.respond(
                authService.registration(
                    registration.flow,
                    listOf(csrfCookie)
                )
            )
        }
    }

    private fun Route.logout() {
        get<AuthRoutes.Logout> {
            val sessionCookie = extractSessionCookie()

            val kratosLogoutResponse =
                kratosClient.getKratosLogout(listOf(sessionCookie))

            call.respondRedirect(kratosLogoutResponse.logoutUrl)
        }
    }

    override val router: Router = {
        index()
        login()
        logout()
        registration()
    }


}


//route definitions
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

    @Serializable
    @Resource("logout")
    class Logout(
        val parent: AuthRoutes = AuthRoutes(),
    )
}

//pipeline plugins
private fun PipelineContext<Unit, ApplicationCall>.extractCsrfCookie(): String {
    val cookie = call.request.cookies.rawCookies.toList().find {
        it.first.contains("csrf_token")
    } ?: throw Error("csrf token not present")

    return "${cookie.first}=${cookie.second}"
}

private fun PipelineContext<Unit, ApplicationCall>.extractSessionCookie(): String {
    val cookie = call.request.cookies.rawCookies.toList().find {
        it.first == "ory_kratos_session"
    } ?: throw Error("Session Cookie not found")
    return "${cookie.first}=${cookie.second}"
}

