package com.espoletatecnologias.api.modules.iam

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.modules.iam.controller.IAMController
import com.espoletatecnologias.api.modules.iam.services.IAMService
import org.koin.core.component.get
import org.koin.dsl.module

class IAMModule : ApplicationModule() {
    override val module = module {
        single { IAMService() }
        single { IAMController(get()) }
    }

    override fun controllers(): List<Controller> =
        listOf(get<IAMController>())
}
