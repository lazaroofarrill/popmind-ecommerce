package com.espoletatecnologias.api.modules.warehouse.products.domain.interfaces

import com.espoletatecnologias.api.clean.crud.CrudRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.ProductVariant

interface ProductVariantRepository : CrudRepository<ProductVariant>
