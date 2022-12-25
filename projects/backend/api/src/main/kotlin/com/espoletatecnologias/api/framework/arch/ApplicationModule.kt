package com.espoletatecnologias.api.framework.arch

import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.reflect.KClass

abstract class ApplicationModule : KoinComponent {
    init {
        println("${this::class.simpleName} module loaded")
    }

    /**
     * Factory function for all instances of the module
     */
    open val module: Module = module { }

    /**
     * Modules this application depends on
     */
    open fun imports(): List<KClass<*>> = emptyList()

    /**
     * List of controllers to bootstrap routes
     */
    open fun controllers(): List<Controller> = emptyList()
}
