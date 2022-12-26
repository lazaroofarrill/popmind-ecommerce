package com.espoletatecnologias.api.modules.warehouse.products.infra.dal.repositories

import com.espoletatecnologias.api.framework.common.exceptions.DalUpdateError
import com.espoletatecnologias.api.framework.common.exceptions.DalWrongColumnContent
import com.espoletatecnologias.api.modules.details.database.crud.ExposedCrudRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.interfaces.ProductVariantRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.ProductVariant
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas.ProductTypeDiscriminator
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas.Products
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

class ProductVariantRepositoryExposed :
    ExposedCrudRepository<ProductVariant>(Products, ResultRow::toVariant),
    ProductVariantRepository {

    override fun commonSelectQuery(): Query {
        return Products.select {
            Products.productTypeDiscriminator eq ProductTypeDiscriminator.VARIANT
        }
    }

    override val insertOverride: InsertStatement<Number>.() -> Unit = {
        this[Products.productTypeDiscriminator] =
            ProductTypeDiscriminator.VARIANT
    }

    override suspend fun update(updatedRecord: ProductVariant): ProductVariant {
        Products.update({
            Products.id eq updatedRecord.id and (
                    Products.productTypeDiscriminator eq
                            ProductTypeDiscriminator.VARIANT)
        }) {
            applyInserts(it, updatedRecord)
        }
        return this.findOne(updatedRecord.id) ?: throw DalUpdateError()
    }
}

private fun ResultRow.toVariant(): ProductVariant {
    return ProductVariant(
        id = this[Products.id],
        parentId = this[Products.parentId]
            ?: throw DalWrongColumnContent("Variants must have  a parent"),
        pictures = try {
            Json.decodeFromString(this[Products.pictures])
        } catch (e: Exception) {
            throw DalWrongColumnContent()
        },
        properties = mapOf()
    )
}
