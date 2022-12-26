package com.espoletatecnologias.api.modules.warehouse.products.domain.services

import com.espoletatecnologias.api.clean.crud.CrudService
import com.espoletatecnologias.api.clean.crud.UnitOfWorkService
import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.CreateCategoryDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.UpdateCategoryDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.ports.CategoryRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Category

class CategoryService(
    categoryRepo: CategoryRepository,
    uwo: UnitOfWorkService
) : CrudService<Category, CreateCategoryDto, UpdateCategoryDto>(
    categoryRepo,
    uwo
)
