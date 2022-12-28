package com.popmind.modules.marketing.business.infra.dal.schemas

import com.espoletatecnologias.common.modules.details.database.crud.CommonTable

object BusinessTable : CommonTable() {
    val name = varchar("name", 250)
    val description = text("description")
    val ownerId = uuid("owner_id")
}
