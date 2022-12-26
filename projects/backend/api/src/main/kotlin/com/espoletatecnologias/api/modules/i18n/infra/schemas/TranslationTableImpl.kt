package com.espoletatecnologias.api.modules.i18n.infra.schemas

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

interface TranslationTable {
    val languageId: Column<UUID>
}

class TranslationTableImpl : Table(), TranslationTable {
    override val languageId: Column<UUID> =
        reference("language_id", Languages.id)
}
