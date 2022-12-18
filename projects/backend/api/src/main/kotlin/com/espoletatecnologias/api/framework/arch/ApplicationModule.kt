package com.espoletatecnologias.api.framework.arch

import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.dsl.module

abstract class ApplicationModule : KoinComponent {
    open val module: Module = module { }

    open val children: List<ApplicationModule> = emptyList()
    open fun controllers(): List<Controller> = emptyList()
}
