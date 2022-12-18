package com.espoletatecnologias.api.modules.common

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import com.espoletatecnologias.api.modules.common.controllers.CommonController
import org.koin.core.component.get
import org.koin.dsl.module

class CommonModule : ApplicationModule() {
    override val module = module {
        single { CommonController() }
    }

    override fun controllers() = listOf(
        get<CommonController>()
    )
}
