package com.espoletatecnologias.api.modules

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import com.espoletatecnologias.api.modules.common.CommonModule
import com.espoletatecnologias.api.modules.iam.IAMModule

class RootModule : ApplicationModule() {
    override val children: List<ApplicationModule> = listOf(
        IAMModule(),
        CommonModule()
    )
}
