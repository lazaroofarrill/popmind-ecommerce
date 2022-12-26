package com.espoletatecnologias.api.modules

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import com.espoletatecnologias.api.modules.details.database.ExposedDatabaseModule
import com.espoletatecnologias.api.modules.iam.IAMModule
import com.espoletatecnologias.api.modules.warehouse.WarehouseModule
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.productSchemas

class RootModule : ApplicationModule() {
    override fun imports() = listOf(
        IAMModule::class,
        WarehouseModule::class,
        ExposedDatabaseModule.bootstrap(
            listOf(productSchemas()).flatten()
        )
    )
}
