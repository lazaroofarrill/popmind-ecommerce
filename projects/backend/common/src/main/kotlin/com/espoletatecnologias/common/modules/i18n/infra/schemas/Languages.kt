package com.espoletatecnologias.common.modules.i18n.infra.schemas

import com.espoletatecnologias.common.modules.details.database.crud.CommonTable

object Languages : CommonTable() {
    val language = varchar("language", 120).uniqueIndex()
    val code = varchar("code", 20).uniqueIndex()
}


