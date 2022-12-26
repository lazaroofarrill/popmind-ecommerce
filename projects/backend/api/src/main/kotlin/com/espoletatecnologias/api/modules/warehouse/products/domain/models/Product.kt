package com.espoletatecnologias.api.modules.warehouse.products.domain.models

import com.espoletatecnologias.api.clean.crud.BaseEntity
import java.util.*


data class Product(
    override val id: UUID,
    val name: String,
    val description: String,
    val pictures: List<String>,
    val categories: List<Category>
) : BaseEntity

data class ProductVariant(
    override val id: UUID,
    val parentId: UUID,
    val properties: Map<VariantProperty, String>,
    val pictures: List<String>
) : BaseEntity

data class VariantProperty(
    override val id: UUID,
    val name: String,
    val allowedValues: List<String>
) : BaseEntity
