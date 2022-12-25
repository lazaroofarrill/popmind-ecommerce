package com.espoletatecnologias.api.modules.warehouse.products.infra.dal

import com.espoletatecnologias.api.framework.common.exceptions.DalInsertError
import com.espoletatecnologias.api.modules.warehouse.products.domain.interfaces.ProductRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import java.util.*

private val products: MutableList<Product> = mutableListOf()

class ExposedProductRepository : ProductRepository {
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
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delete(ids: List<UUID>): Int {
        TODO("Not yet implemented")
    }
}
