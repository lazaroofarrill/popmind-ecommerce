package com.espoletatecnologias.api.modules.iam.controller

import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.modules.iam.services.IAMService
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import kotlinx.serialization.Serializable


class IAMController(private val iamService: IAMService) : Controller {
    private fun Route.login() {
        post<IAMRoutes.Login> {
            val formParameters = call.receiveParameters()
            val username: String = formParameters["username"].toString()
            val password: String = formParameters["password"].toString()

            call.respondText(
                """
                username: $username
                password: $password
            """.trimIndent()
            )
        }
    }

    private fun Route.logout() {
        get<IAMRoutes.Logout> {
            call.respondText(iamService.logout())
        }
    }

    private fun Route.landing() {
        get<IAMRoutes> {
            call.respondHtml {
                head {
                    title { +"IAM" }
                }
                body {
                    h1 { +"IAM" }
                    p { +"This is the IAM module" }

                    form(
                        action = "/iam/login", method = FormMethod.post,
                        encType = FormEncType.applicationXWwwFormUrlEncoded
                    ) {

                        p {
                            +"Username:"
                            textInput { name = "username" }
                        }
                        p {
                            +"Password:"
                            passwordInput { name = "password" }
                        }
                        p {
                            submitInput { value = "Login" }
                        }
                    }
                }
            }
        }
    }

    override val router: Route.() -> Unit = {
        login()
        logout()
        landing()
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
}
