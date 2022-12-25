package com.espoletatecnologias.api.modules.warehouse.products.infra.dal

import com.espoletatecnologias.api.framework.common.exceptions.DalInsertError
import com.espoletatecnologias.api.framework.common.exceptions.DalUpdateError
import com.espoletatecnologias.api.modules.warehouse.products.domain.interfaces.ProductRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import java.util.*

private val products: MutableList<Product> = mutableListOf()

class InMemoryProductRepository : ProductRepository {
    override suspend fun find(): List<Product> {
        return products.toList()
    }

    override suspend fun findOne(id: UUID): Product? {
        return products.find { it.id == id }
    }

    override suspend fun create(newProduct: Product): Product {
        return if (products.add(newProduct)) {
            newProduct
        } else throw DalInsertError()
    }

    override suspend fun update(updatedProduct: Product): Product {
        val toUpdateIndex = products.indexOfFirst {
            it.id == updatedProduct.id
        }
        if (toUpdateIndex == -1) {
            throw DalUpdateError()
        }
        products[toUpdateIndex] = updatedProduct
        return updatedProduct
    }

    override suspend fun delete(id: UUID): Boolean {
        return products.removeIf { it.id == id }
    }

    override suspend fun delete(ids: List<UUID>): Boolean {
        return ids.map { id2Delete ->
            products.removeIf { it.id == id2Delete }
        }.contains(false).not()
    }
}
