package com.espoletatecnologias.common.modules.errors

import com.espoletatecnologias.common.framework.arch.ApplicationModule
import com.espoletatecnologias.common.framework.arch.Controller
import com.espoletatecnologias.common.modules.errors.controllers.ErrorsController
import com.espoletatecnologias.common.modules.errors.services.ErrorsService
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
