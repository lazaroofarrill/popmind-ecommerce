package com.espoletatecnologias.api.modules.warehouse

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import com.espoletatecnologias.api.modules.warehouse.products.ProductsModule
import kotlin.reflect.KClass

class WarehouseModule: ApplicationModule() {
    override fun imports(): List<KClass<*>> = listOf(
        ProductsModule::class
    )
}
