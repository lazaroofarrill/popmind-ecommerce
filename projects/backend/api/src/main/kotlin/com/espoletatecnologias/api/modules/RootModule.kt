package com.espoletatecnologias.api.modules

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import com.espoletatecnologias.api.modules.iam.IAMModule
import com.espoletatecnologias.api.modules.warehouse.products.ProductsModule

class RootModule : ApplicationModule() {
    override fun imports() = listOf(
        IAMModule::class,
        ProductsModule::class
    )
}
