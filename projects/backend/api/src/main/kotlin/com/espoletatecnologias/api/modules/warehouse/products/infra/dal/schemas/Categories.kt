package com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas

import com.espoletatecnologias.api.modules.details.database.crud.CommonTable
import com.espoletatecnologias.api.modules.i18n.infra.schemas.TranslationTable
import com.espoletatecnologias.api.modules.i18n.infra.schemas.TranslationTableImpl
import org.jetbrains.exposed.sql.Table

abstract class CategoriesBase :
    CommonTable() {
    val name = varchar("name", 250)
    val description = text("description")
    val parentId = reference("parent_id", id).nullable()
}

object Categories : CategoriesBase()


abstract class CategoriesTranslationsBase(translationTable: TranslationTable = TranslationTableImpl()) :
    CategoriesBase(), TranslationTable by translationTable {
}

object CategoriesTranslations : CategoriesTranslationsBase()

@Suppress("MemberVisibilityCanBePrivate")
object CategoriesProductsJoint : Table() {
    val categoryId = reference("category_id", Categories.id)
    val productId = reference("product_id", Products.id)

    override val primaryKey = PrimaryKey(categoryId, productId)
}
