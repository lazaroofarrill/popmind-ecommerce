package com.espoletatecnologias.api.modules.warehouse.products.domain.services

import com.espoletatecnologias.common.clean.crud.CrudService
import com.espoletatecnologias.common.clean.crud.UnitOfWorkService
import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.CreateProductDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.UpdateProductDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import com.espoletatecnologias.api.modules.warehouse.products.domain.ports.ProductRepository
import com.espoletatecnologias.common.framework.common.exceptions.DalInsertError
import com.espoletatecnologias.common.framework.common.exceptions.DalUpdateError


class ProductService(
    private val productRepo: ProductRepository,
    private val uwo: UnitOfWorkService
) :
    CrudService<Product, CreateProductDto, UpdateProductDto>(productRepo, uwo) {
    override suspend fun create(createDto: CreateProductDto): Product {
        return uwo.exec {
            val createdProduct = super.create(createDto)

            createDto.categoryIds?.let {
                productRepo.updateRelationWithCategories(
                    createdProduct.id,
                    it
                )
            }

            findOne(createdProduct.id) ?: throw DalInsertError()
        }
    }

    override suspend fun update(updateDto: UpdateProductDto): Product {
        return uwo.exec {
            val updatedProduct = super.update(updateDto)

            updateDto.categoryIds?.let {
                productRepo.updateRelationWithCategories(updatedProduct.id, it)
            }

            findOne(updatedProduct.id) ?: throw DalUpdateError()
        }
    }
}
