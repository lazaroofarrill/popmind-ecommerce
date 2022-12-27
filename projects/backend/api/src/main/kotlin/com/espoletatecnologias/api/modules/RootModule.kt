package com.espoletatecnologias.api.modules


import com.espoletatecnologias.api.modules.warehouse.WarehouseModule
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas.*
import com.espoletatecnologias.common.framework.arch.ApplicationModule
import com.espoletatecnologias.common.modules.details.database.ExposedDatabaseModule
import com.espoletatecnologias.common.modules.i18n.infra.schemas.Languages
import com.espoletatecnologias.common.modules.iam.IAMModule

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
