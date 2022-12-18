package com.espoletatecnologias.api.framework.plugins

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import io.ktor.server.application.*
import org.koin.core.KoinApplication
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(rootModule: ApplicationModule) {
    install(Koin) {
        slf4jLogger()

        bootstrapModules(rootModule)
    }
}

fun KoinApplication.bootstrapModules(module: ApplicationModule) {
    module.children.forEach { child ->
        bootstrapModules(child)
    }
    modules(module.module)
}
