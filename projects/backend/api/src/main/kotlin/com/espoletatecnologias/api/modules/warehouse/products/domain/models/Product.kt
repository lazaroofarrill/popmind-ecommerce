package com.espoletatecnologias.api.modules.warehouse.products.domain.models

import java.util.*


data class Product(
    val id: UUID,
    val name: String,
    val description: String,
    val pictures: List<String>,
    val categories: List<Category>
)

data class ProductVariant(
    val id: UUID,
    val parent: Product,
    val properties: Map<VariantProperty, String>,
    val pictures: List<String>
)

data class VariantProperty(
    val id: UUID,
    val name: String,
    val allowedValues: List<String>
)
