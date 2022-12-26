package com.espoletatecnologias.api.modules

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import com.espoletatecnologias.api.modules.details.database.ExposedDatabaseModule
import com.espoletatecnologias.api.modules.i18n.infra.schemas.Languages
import com.espoletatecnologias.api.modules.iam.IAMModule
import com.espoletatecnologias.api.modules.warehouse.WarehouseModule
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas.*

class RootModule : ApplicationModule() {
    override fun imports() = listOf(
        IAMModule::class,
        WarehouseModule::class,
        ExposedDatabaseModule.bootstrap(
            listOf(
                Languages,
                Products,
                ProductsTranslations,
                Categories,
                CategoriesTranslations,
                CategoriesProductsJoint
            )
        )
    )
}
