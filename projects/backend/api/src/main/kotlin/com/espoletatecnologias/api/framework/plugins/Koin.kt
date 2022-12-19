package com.espoletatecnologias.api.framework.plugins

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import io.ktor.server.application.*
import org.koin.core.KoinApplication
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(rootModule: ApplicationModule) {
    install(Koin) {
        slf4jLogger()
        bootstrapDI(rootModule)
    }
}

fun KoinApplication.bootstrapDI(
    module: ApplicationModule,
) {
    module.children.forEach { child ->
        bootstrapDI(child)
    }
    modules(module.module)
}
