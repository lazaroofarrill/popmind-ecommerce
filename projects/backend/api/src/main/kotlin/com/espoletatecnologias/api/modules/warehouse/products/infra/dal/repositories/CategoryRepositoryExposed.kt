package com.espoletatecnologias.api.modules.warehouse.products.infra.dal.repositories

import com.espoletatecnologias.api.modules.details.database.crud.ExposedCrudRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.ports.CategoryRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Category
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas.Categories
import org.jetbrains.exposed.sql.ResultRow

class CategoryRepositoryExposed :
    ExposedCrudRepository<Category>(Categories, ResultRow::toCategory),
    CategoryRepository

private fun ResultRow.toCategory(): Category {
    return Category(
        id = this[Categories.id],
        name = this[Categories.name],
        description = this[Categories.description],
        parent = null
    )
}
