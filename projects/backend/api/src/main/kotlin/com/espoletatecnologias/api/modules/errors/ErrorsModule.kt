package com.espoletatecnologias.api.modules.errors

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.modules.errors.controllers.ErrorsController
import com.espoletatecnologias.api.modules.errors.services.ErrorsService
import org.koin.core.component.get
import org.koin.dsl.module

class ErrorsModule : ApplicationModule() {
    override val module = module {
        single { ErrorsService() }
        single { ErrorsController() }
    }

    override fun controllers(): List<Controller> = listOf(
        get<ErrorsController>()
    )
}
