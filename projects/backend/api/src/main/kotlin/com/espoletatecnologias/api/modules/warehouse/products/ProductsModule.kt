package com.espoletatecnologias.api.modules.warehouse.products

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.modules.warehouse.products.domain.ports.CategoryRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.ports.ProductRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.services.CategoryService
import com.espoletatecnologias.api.modules.warehouse.products.domain.services.ProductService
import com.espoletatecnologias.api.modules.warehouse.products.infra.controllers.CategoryController
import com.espoletatecnologias.api.modules.warehouse.products.infra.controllers.ProductController
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.repositories.CategoryRepositoryExposed
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.repositories.ProductRepositoryExposed
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.dsl.module

@Suppress("USELESS_CAST")
class ProductsModule : ApplicationModule() {
    override val module: Module = module {
        single { ProductService(productRepo = get(), uwo = get()) }
        single { CategoryService(categoryRepo = get(), uwo = get()) }
        single { ProductRepositoryExposed() as ProductRepository }
        single { CategoryRepositoryExposed() as CategoryRepository }
    }

    override fun controllers(): List<Controller> = listOf(
        ProductController(productService = get()),
        CategoryController(categoryService = get())
    )
}
