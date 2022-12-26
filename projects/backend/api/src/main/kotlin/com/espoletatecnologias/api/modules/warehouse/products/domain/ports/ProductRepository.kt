package com.espoletatecnologias.api.modules.warehouse.products.domain.ports

import com.espoletatecnologias.api.clean.crud.CrudRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product

interface ProductRepository : CrudRepository<Product>
