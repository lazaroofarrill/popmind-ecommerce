package com.espoletatecnologias.api.modules.warehouse.products.infra.dal.repositories

import com.espoletatecnologias.api.framework.common.exceptions.DalUpdateError
import com.espoletatecnologias.api.framework.common.exceptions.DalWrongColumnContent
import com.espoletatecnologias.api.modules.details.database.crud.ExposedCrudRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.ports.ProductRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas.ProductTypeDiscriminator
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas.Products
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

private fun ResultRow.toProduct(): Product {
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

class ProductRepositoryExposed :
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


