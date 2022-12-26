package com.espoletatecnologias.api.modules.warehouse.products.infra.dal.repositories

import com.espoletatecnologias.api.clean.crud.FindManyOptions
import com.espoletatecnologias.api.clean.crud.FindManyResponse
import com.espoletatecnologias.api.clean.crud.FindOptions
import com.espoletatecnologias.api.framework.common.exceptions.DalUpdateError
import com.espoletatecnologias.api.framework.common.exceptions.DalWrongColumnContent
import com.espoletatecnologias.api.modules.details.database.crud.ExposedCrudRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import com.espoletatecnologias.api.modules.warehouse.products.domain.ports.ProductRepository
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas.Categories
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas.CategoriesProductsJoint
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas.ProductTypeDiscriminator
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas.Products
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import java.util.*

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

    override suspend fun updateRelationWithCategories(
        productId: UUID,
        categoryIds: List<UUID>
    ) {
        CategoriesProductsJoint.deleteWhere {
            CategoriesProductsJoint.productId eq productId
        }
        CategoriesProductsJoint.batchInsert(categoryIds) { categoryId ->
            this[CategoriesProductsJoint.productId] = productId
            this[CategoriesProductsJoint.categoryId] = categoryId
        }
    }


    override suspend fun find(options: FindManyOptions): FindManyResponse<Product> {
        val query = Products.join(
            otherTable = CategoriesProductsJoint.join(
                otherTable = Categories,
                joinType = JoinType.INNER,
                additionalConstraint = { CategoriesProductsJoint.categoryId eq Categories.id }
            ),
            joinType = JoinType.LEFT,
            additionalConstraint = { Products.id eq CategoriesProductsJoint.productId }
        ).selectAll()

        commonSelectQuery(query)
        applyFiltersToQuery(query, options.where)

        val findResult = super.find(options)
        val resultWithJoins = findResult.copy(
            items = findResult.items.map { currentProduct ->
                currentProduct.copy(
                    categories = query.filter {
                        it[CategoriesProductsJoint.productId] == currentProduct.id
                    }.map(ResultRow::toCategory)
                )
            }
        )
        return resultWithJoins
    }

    override suspend fun findOne(options: FindOptions): Product? {
        val query = Products.join(
            otherTable = CategoriesProductsJoint
                .join(
                    otherTable = Categories,
                    joinType = JoinType.INNER,
                    additionalConstraint = {
                        Categories.id eq CategoriesProductsJoint.categoryId
                    }
                ),
            joinType = JoinType.LEFT,
            additionalConstraint = {
                Products.id eq CategoriesProductsJoint.productId
            }
        ).selectAll()

        commonSelectQuery(query)
        applyFiltersToQuery(query, options.where)


        val foundProduct = super.findOne(options)
        val productWithJoins = foundProduct?.copy(
            categories = query.filter {
                it[Products.id] == foundProduct.id
            }.map(ResultRow::toCategory)
        )
        return productWithJoins
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


