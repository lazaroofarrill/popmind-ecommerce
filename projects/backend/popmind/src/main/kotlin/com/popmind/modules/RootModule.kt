package com.popmind.modules

import com.espoletatecnologias.common.framework.arch.ApplicationModule
import com.popmind.modules.marketing.business.BusinessModule
import kotlin.reflect.KClass

class RootModule : ApplicationModule() {
    override fun imports(): List<KClass<*>> = listOf(BusinessModule::class)
}
