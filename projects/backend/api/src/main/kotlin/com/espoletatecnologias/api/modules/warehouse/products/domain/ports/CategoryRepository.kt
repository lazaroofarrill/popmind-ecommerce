package com.espoletatecnologias.api.modules.warehouse.products.domain.ports

import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Category
import com.espoletatecnologias.common.clean.crud.CrudRepository

interface CategoryRepository : CrudRepository<Category>
