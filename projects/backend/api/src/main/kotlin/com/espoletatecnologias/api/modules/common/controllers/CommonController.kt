package com.espoletatecnologias.api.modules.common.controllers

import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.framework.types.Router
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.*
import kotlinx.serialization.Serializable


class CommonController : Controller {
    private val styles: Router = {
        get<Routes.Styles> {
            call.respondCss {
                body {
                    color = Color.white
                    backgroundColor = Color("#202A44")
                }

                input {
                    color = Color.blueViolet
                    backgroundColor = Color.white
                    fontSize = 16.pt
                    borderRadius = 24.px
                    padding(8.px, 16.px)
                    minWidth = 64.px
                }

                rule(".layout") {
                    display = Display.flex
                    flexDirection = FlexDirection.column
                    justifyContent = JustifyContent.center
                    alignItems = Align.center
                    gap = 32.px
                }
            }
        }
    }


    override val router: Router = {
        styles()
    }

    @Suppress("unused")
    @Serializable
    @Resource("")
    private class Routes {
        @Serializable
        @Resource("styles.css")
        class Styles(val parent: Routes)
    }
}

suspend inline fun ApplicationCall.respondCss(builder: CssBuilder.() -> Unit) {
    this.respondText(
        CssBuilder().apply(builder).toString(),
        ContentType.Text.CSS
    )
}
