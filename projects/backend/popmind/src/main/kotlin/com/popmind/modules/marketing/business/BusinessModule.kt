package com.popmind.modules.marketing.business

import com.espoletatecnologias.common.framework.arch.ApplicationModule
import com.espoletatecnologias.common.framework.arch.Controller
import com.popmind.modules.marketing.business.domain.ports.BusinessService
import com.popmind.modules.marketing.business.domain.services.BusinessServiceImpl
import com.popmind.modules.marketing.business.infra.controllers.BusinessController
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.dsl.module

@Suppress("USELESS_CAST")
class BusinessModule : ApplicationModule() {
    override val module: Module = module {
        single { BusinessServiceImpl(get(), get(), get()) as BusinessService }
    }

    override fun controllers(): List<Controller> = listOf(
        BusinessController(get())
    )
}
