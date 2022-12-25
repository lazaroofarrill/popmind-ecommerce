package com.espoletatecnologias.api.modules.warehouse.products

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.modules.warehouse.products.domain.interfaces.ProductRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.services.ProductService
import com.espoletatecnologias.api.modules.warehouse.products.infra.controllers.ProductController
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.ExposedProductRepository
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.dsl.module

@Suppress("USELESS_CAST")
class ProductsModule : ApplicationModule() {
    override val module: Module = module {
        single { ProductService(productRepo = get()) }
        single { ExposedProductRepository() as ProductRepository }

    }

    override fun controllers(): List<Controller> = listOf(
        ProductController(productService = get())
    )
}
