package com.espoletatecnologias.api.modules.iam.controller

import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.modules.iam.services.IAMService
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


class IAMController(private val iamService: IAMService) : Controller {
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

    private fun Route.landing() {
        get<IAMRoutes> {
            call.respondHtml {
                head {
                    title { +"IAM" }
                    link { rel = "stylesheet"; href = "/styles.css" }
                }
                body {
                    section(classes = "layout") {
                        h1 { +"IAM" }
                        p { +"This is the IAM module" }

                        form(
                            action = "/iam/login", method = FormMethod.post,
                            encType = FormEncType.applicationXWwwFormUrlEncoded,
                            classes = "layout"
                        ) {

                            div() {
                                label {
                                    +"Username:"
                                }
                                textInput { name = "username" }
                            }

                            div {
                                label {
                                    +"Password:"
                                }
                                passwordInput { name = "password" }
                            }
                            submitInput { value = "Login" }

                        }
                    }
                }
            }
        }
    }

    override val router: Route.() -> Unit = {
        landing()
        login()
        logout()
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
