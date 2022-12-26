package com.espoletatecnologias.api.modules.warehouse.products.infra.dal.repositories

import com.espoletatecnologias.api.clean.crud.FindManyOptions
import com.espoletatecnologias.api.clean.crud.FindManyResponse
import com.espoletatecnologias.api.clean.crud.FindOptions
import com.espoletatecnologias.api.framework.common.exceptions.DalInsertError
import com.espoletatecnologias.api.framework.common.exceptions.DalUpdateError
import com.espoletatecnologias.api.modules.warehouse.products.domain.ports.ProductRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import java.util.*

private val products: MutableList<Product> = mutableListOf()

class InMemoryProductRepository : ProductRepository {
    override suspend fun find(options: FindManyOptions): FindManyResponse<Product> {
        val items = products.toList()

        return FindManyResponse(
            items = items,
            limit = options.limit,
            offset = options.offset,
            count = items.size.toLong(),
            total = products.size.toLong()
        )
    }

    override suspend fun findOne(id: UUID): Product? {
        return products.find { it.id == id }
    }

    override suspend fun findOne(options: FindOptions): Product? {
        TODO("Not yet implemented")
    }

    override suspend fun create(newRecord: Product): Product {
        return if (products.add(newRecord)) {
            newRecord
        } else throw DalInsertError()
    }

    override suspend fun update(updatedRecord: Product): Product {
        val toUpdateIndex = products.indexOfFirst {
            it.id == updatedRecord.id
        }
        if (toUpdateIndex == -1) {
            throw DalUpdateError()
        }
        products[toUpdateIndex] = updatedRecord
        return updatedRecord
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
