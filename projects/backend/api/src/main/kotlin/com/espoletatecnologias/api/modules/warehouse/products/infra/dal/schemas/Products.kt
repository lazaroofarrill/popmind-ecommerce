package com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas

import com.espoletatecnologias.api.modules.details.database.crud.CommonTable
import com.espoletatecnologias.api.modules.i18n.infra.schemas.TranslationTable
import com.espoletatecnologias.api.modules.i18n.infra.schemas.TranslationTableImpl

abstract class ProductsBase : CommonTable() {
    val name = varchar("name", 250).nullable()
    val description = text("description").nullable()
    val parentId = reference("parent_id", id).nullable()
    val pictures = text("pictures").default("")
    val productTypeDiscriminator =
        enumeration<ProductTypeDiscriminator>("product_type_discriminator")
}

object Products : ProductsBase()


abstract class ProductsTranslationsBase(
    translationTable: TranslationTable = TranslationTableImpl()
) : ProductsBase(), TranslationTable by translationTable


object ProductsTranslations : ProductsTranslationsBase()
