package com.espoletatecnologias.api.modules.i18n.infra.schemas

import com.espoletatecnologias.api.modules.details.database.crud.CommonTable
import org.jetbrains.exposed.sql.Column
import java.util.*

object Languages : CommonTable() {
    val language = varchar("language", 120).uniqueIndex()
    val code = varchar("code", 20).uniqueIndex()
}


