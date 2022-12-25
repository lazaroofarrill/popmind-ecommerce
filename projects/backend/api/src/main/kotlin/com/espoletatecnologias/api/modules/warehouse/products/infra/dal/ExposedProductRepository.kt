package com.espoletatecnologias.api.modules.warehouse.products.infra.dal

import com.espoletatecnologias.api.framework.common.exceptions.DalInsertError
import com.espoletatecnologias.api.framework.common.exceptions.DalUpdateError
import com.espoletatecnologias.api.framework.common.exceptions.DalWrongColumnContent
import com.espoletatecnologias.api.modules.warehouse.products.domain.interfaces.ProductRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import java.util.*

enum class ProductTypeDiscriminator {
    PRODUCT,
    VARIANT
}

private object Products : Table() {
    val id = uuid("id").autoGenerate()
    val name = varchar("name", 250).nullable()
    val description = text("description").nullable()
    val parentId = reference("parent_id", id).nullable()
    val pictures = text("pictures").default("")
    val productTypeDiscriminator =
        enumeration<ProductTypeDiscriminator>("product_type_discriminator")


    override val primaryKey = PrimaryKey(id)
}

fun ResultRow.toProduct(): Product {
    return Product(
        id = this[Products.id],
        name = this[Products.name] ?: throw DalWrongColumnContent(),
        description = this[Products.description]
            ?: throw DalWrongColumnContent(),
        categories = emptyList(),
        pictures = emptyList(),
    )
}

class ExposedProductRepository : ProductRepository {
    override suspend fun find(): List<Product> {
        return Products.select {
            Products.productTypeDiscriminator eq ProductTypeDiscriminator.PRODUCT
        }.map(ResultRow::toProduct)
    }

    override suspend fun findOne(id: UUID): Product? {
        val query =
            Products.select { Products.productTypeDiscriminator eq ProductTypeDiscriminator.PRODUCT }
                .andWhere {
                    Products.id eq id
                }

        return query.singleOrNull()?.let(ResultRow::toProduct)
    }

    override suspend fun create(newProduct: Product): Product {
        val insertStatement = Products.insert {
            it[name] = newProduct.name
            it[description] = newProduct.description
            it[productTypeDiscriminator] = ProductTypeDiscriminator.PRODUCT
        }

        return insertStatement.resultedValues?.singleOrNull()
            ?.let(ResultRow::toProduct)
            ?: throw DalInsertError()
    }

    override suspend fun update(updatedProduct: Product): Product {
        Products.update({ Products.id eq updatedProduct.id }) {
            it[name] = updatedProduct.name
            it[description] = updatedProduct.description
        }
        return this.findOne(updatedProduct.id) ?: throw DalUpdateError()
    }

    override suspend fun delete(id: UUID): Boolean {
        return Products.deleteWhere { Products.id eq id } > 0
    }

    override suspend fun delete(ids: List<UUID>): Boolean {
        return Products.deleteWhere {
            id inList (ids)
        } == ids.size
    }
}

fun productSchemas() = listOf<Table>(
    Products
)
