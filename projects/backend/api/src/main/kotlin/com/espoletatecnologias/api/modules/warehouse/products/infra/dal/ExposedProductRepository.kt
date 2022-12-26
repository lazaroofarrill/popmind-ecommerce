package com.espoletatecnologias.api.modules.warehouse.products.infra.dal

import com.espoletatecnologias.api.framework.common.exceptions.DalUpdateError
import com.espoletatecnologias.api.framework.common.exceptions.DalWrongColumnContent
import com.espoletatecnologias.api.modules.details.database.crud.CommonTable
import com.espoletatecnologias.api.modules.details.database.crud.ExposedCrudRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.interfaces.ProductRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.interfaces.ProductVariantRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.ProductVariant
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

enum class ProductTypeDiscriminator {
    PRODUCT,
    VARIANT
}

private object Products : CommonTable() {
    val name = varchar("name", 250).nullable()
    val description = text("description").nullable()
    val parentId = reference("parent_id", id).nullable()
    val pictures = text("pictures").default("")
    val productTypeDiscriminator =
        enumeration<ProductTypeDiscriminator>("product_type_discriminator")
}

fun ResultRow.toProduct(): Product {

    return Product(
        id = this[Products.id],
        name = try {
            this[Products.name] ?: throw DalWrongColumnContent()
        } catch (e: Exception) {
            println(this[Products.name])
            throw e
        },
        description = this[Products.description]
            ?: throw DalWrongColumnContent(),
        categories = emptyList(),
        pictures = try {
            Json.decodeFromString(this[Products.pictures])
        } catch (e: Exception) {
            emptyList()
        },
    )
}

class ExposedProductRepository :
    ExposedCrudRepository<Product>(Products, ResultRow::toProduct),
    ProductRepository {
    override fun commonSelectQuery(): Query {
        return Products.select {
            Products.productTypeDiscriminator eq ProductTypeDiscriminator.PRODUCT
        }
    }

    override val insertOverride: InsertStatement<Number>.() -> Unit = {
        this[Products.productTypeDiscriminator] =
            ProductTypeDiscriminator.PRODUCT
    }

    override suspend fun update(updatedRecord: Product): Product {
        Products.update({
            Products.id eq updatedRecord.id and (
                    Products.productTypeDiscriminator eq
                            ProductTypeDiscriminator.PRODUCT)
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

class ExposedProductVariantRepository :
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

fun productSchemas() = listOf<Table>(
    Products
)
