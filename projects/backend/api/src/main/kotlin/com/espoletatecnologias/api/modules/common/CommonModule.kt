package com.espoletatecnologias.api.modules.common

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import com.espoletatecnologias.api.modules.common.services.provideHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

class CommonModule : ApplicationModule() {
    override val module: Module = module {
        provideHttpClient()
    }
}
