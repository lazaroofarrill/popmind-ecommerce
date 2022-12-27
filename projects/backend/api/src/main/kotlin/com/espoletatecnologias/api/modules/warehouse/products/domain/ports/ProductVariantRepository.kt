package com.espoletatecnologias.api.modules.warehouse.products.domain.ports

import com.espoletatecnologias.common.clean.crud.CrudRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.ProductVariant

interface ProductVariantRepository : CrudRepository<ProductVariant>
