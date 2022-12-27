package com.espoletatecnologias.common.modules.common

import com.espoletatecnologias.common.framework.arch.ApplicationModule
import com.espoletatecnologias.common.modules.common.services.provideHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

class CommonModule : ApplicationModule() {
    override val module: Module = module {
        provideHttpClient()
    }
}
