package com.espoletatecnologias.api.modules.warehouse.products.infra.dal

import com.espoletatecnologias.api.clean.crud.FindManyOptions
import com.espoletatecnologias.api.clean.crud.FindManyResponse
import com.espoletatecnologias.api.clean.crud.FindOptions
import com.espoletatecnologias.api.framework.common.exceptions.DalInsertError
import com.espoletatecnologias.api.framework.common.exceptions.DalUpdateError
import com.espoletatecnologias.api.framework.common.exceptions.DalWrongColumnContent
import com.espoletatecnologias.api.modules.warehouse.products.domain.interfaces.ProductRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import java.util.*
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

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
        pictures = try {
            Json.decodeFromString(this[Products.pictures])
        } catch (e: Exception) {
            emptyList()
        },
    )
}

class ExposedProductRepository : ProductRepository {
    private fun commonSelectQuery(): Query {
        return Products.select {
            Products.productTypeDiscriminator eq ProductTypeDiscriminator.PRODUCT
        }
    }

    private fun applyFiltersToQuery(
        query: Query,
        where: Map<Any, Any>
    ): Query {
        where.forEach { filter ->
            if (filter.key is String) {
                val column: Column<*> =
                    Products::class.declaredMemberProperties.find {
                        it.name == filter.key
                    }?.let {
                        it.isAccessible = true
                        if (it.get(Products) is Column<*>) {
                            it.get(Products) as Column<*>
                        } else {
                            throw Error("Property is not of type column")
                        }
                    } ?: throw Error("Column for filtering not found")

                query.andWhere {
                    column as Column<String> eq filter.value as String
                }
            } else {
                throw Error("This repository only handles string keys")
            }

        }

        return query
    }

    override suspend fun find(options: FindManyOptions): FindManyResponse<Product> {
        val query = commonSelectQuery()

        applyFiltersToQuery(query, options.where)

        val totalCount = query.count()

        query.limit(options.limit, options.offset)

        val queryCount = query.count()

        val items = query.map(ResultRow::toProduct)

        return FindManyResponse(
            items = items,
            limit = options.limit,
            count = queryCount,
            offset = options.offset,
            total = totalCount
        )
    }

    override suspend fun findOne(id: UUID): Product? {
        val query =
            commonSelectQuery()
                .andWhere {
                    Products.id eq id
                }

        return query.singleOrNull()?.let(ResultRow::toProduct)
    }

    override suspend fun findOne(options: FindOptions): Product? {
        val query = commonSelectQuery()

        applyFiltersToQuery(query, options.where)

        return query.singleOrNull()?.let(ResultRow::toProduct)
    }

    override suspend fun create(newProduct: Product): Product {
        val insertStatement = Products.insert {
            it[name] = newProduct.name
            it[description] = newProduct.description
            it[productTypeDiscriminator] = ProductTypeDiscriminator.PRODUCT
            it[pictures] = Json.encodeToString(newProduct.pictures)
        }

        return insertStatement.resultedValues?.singleOrNull()
            ?.let(ResultRow::toProduct)
            ?: throw DalInsertError()
    }

    override suspend fun update(updatedProduct: Product): Product {
        Products.update({
            Products.id eq updatedProduct.id and (
                    Products.productTypeDiscriminator eq
                            ProductTypeDiscriminator.PRODUCT)
        }) {
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
