package com.espoletatecnologias.common.framework.plugins

import com.espoletatecnologias.common.framework.arch.ApplicationModule
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
    return loadedModules.toList()
}

//all the modules used in this application are stored here
private val loadedModules = mutableListOf<ApplicationModule>()

private fun KoinApplication.bootstrapDI(module: ApplicationModule) {
    if (loadedModules.map { it::class }.contains(module::class)) {
        return
    }
    loadedModules.add(module)
    module.imports().forEach { child ->
        if (!child.isSubclassOf(ApplicationModule::class)) {
            throw Error(
                """${child.qualifiedName} is not a valid application module. 
                        Application modules are subclasses of ${ApplicationModule::class.qualifiedName}""".trimMargin()
            )
        }
        bootstrapDI(child.createInstance() as ApplicationModule)
    }
    modules(module.module)
}
