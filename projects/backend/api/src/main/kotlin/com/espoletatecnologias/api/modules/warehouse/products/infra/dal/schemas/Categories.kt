package com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas

import com.espoletatecnologias.api.modules.details.database.crud.CommonTable
import com.espoletatecnologias.api.modules.i18n.infra.schemas.TranslationTable
import com.espoletatecnologias.api.modules.i18n.infra.schemas.TranslationTableImpl

abstract class CategoriesBase :
    CommonTable() {
    val name = varchar("name", 250)
    val description = text("description")
}

object Categories : CategoriesBase()


abstract class CategoriesTranslationsBase(translationTable: TranslationTable = TranslationTableImpl()) :
    CategoriesBase(), TranslationTable by translationTable {
}

object CategoriesTranslations : CategoriesTranslationsBase()
