package com.espoletatecnologias.api.framework.plugins

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import io.ktor.server.application.*
import org.koin.core.KoinApplication
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.isSubclassOf

fun <T : ApplicationModule> Application.configureKoin(rootModuleCtr: KClass<T>): List<ApplicationModule> {
    install(Koin) {
        slf4jLogger()
        val rootModuleInstance = rootModuleCtr.createInstance()
        bootstrapDI(rootModuleInstance)
    }
    return usedModules.toList()
}

//all the modules used in this application are stored here
private val usedModules = mutableListOf<ApplicationModule>()

fun KoinApplication.bootstrapDI(module: ApplicationModule) {
    if (usedModules.map { it::class }.contains(module::class)) {
        return
    }
    usedModules.add(module)
    module.imports().forEach { child ->
        if (!child.isSubclassOf(ApplicationModule::class)) {
            throw Error(
                "${child.qualifiedName} is not a valid application module. " +
                        "Application modules are subclasses of ${ApplicationModule::class.qualifiedName}"
            )
        }
        bootstrapDI(child.createInstance() as ApplicationModule)
    }
    modules(module.module)
}
