package com.popmind.modules.marketing.business.infra.dal.repositories

import com.espoletatecnologias.common.modules.details.database.crud.ExposedCrudRepository
import com.popmind.modules.marketing.business.domain.models.Business
import com.popmind.modules.marketing.business.domain.ports.BusinessRepo
import com.popmind.modules.marketing.business.infra.dal.schemas.BusinessTable
import org.jetbrains.exposed.sql.ResultRow

fun resultToBusiness(resultRow: ResultRow): Business {
    return Business(
        id = resultRow[BusinessTable.id],
        name = resultRow[BusinessTable.name],
        description = resultRow[BusinessTable.description],
        categories = emptyList(),
        menus = emptyList()
    )
}

class BusinessRepoImpl :
    ExposedCrudRepository<Business>(BusinessTable, ::resultToBusiness),
    BusinessRepo
