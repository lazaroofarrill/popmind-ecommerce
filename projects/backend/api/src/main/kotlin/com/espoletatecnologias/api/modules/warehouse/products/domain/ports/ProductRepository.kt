package com.espoletatecnologias.api.modules.warehouse.products.domain.ports

import com.espoletatecnologias.api.clean.crud.CrudRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import java.util.*

interface ProductRepository : CrudRepository<Product> {
    suspend fun updateRelationWithCategories(
        productId: UUID,
        categoryIds: List<UUID>
    )
}
