package com.espoletatecnologias.api.modules.warehouse.products.infra.dal.repositories

import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Category
import com.espoletatecnologias.api.modules.warehouse.products.domain.ports.CategoryRepository
import com.espoletatecnologias.api.modules.warehouse.products.infra.dal.schemas.Categories
import com.espoletatecnologias.common.modules.details.database.crud.ExposedCrudRepository
import org.jetbrains.exposed.sql.ResultRow

class CategoryRepositoryExposed :
    ExposedCrudRepository<Category>(Categories, ResultRow::toCategory),
    CategoryRepository

fun ResultRow.toCategory(): Category {
    return Category(
        id = this[Categories.id],
        name = this[Categories.name],
        description = this[Categories.description],
        parent = null,
        parentId = this[Categories.parentId]
    )
}
