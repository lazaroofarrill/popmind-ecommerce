package com.espoletatecnologias.api.modules.warehouse.products.domain.services

import com.espoletatecnologias.api.clean.crud.CrudService
import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.CreateProductDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.interfaces.ProductRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product


class ProductService(productRepo: ProductRepository) :
    CrudService<Product, CreateProductDto, CreateProductDto>(productRepo)
