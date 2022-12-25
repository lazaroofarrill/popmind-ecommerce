package com.espoletatecnologias.api.modules.warehouse.products.domain.models

import java.util.*

interface IProduct {
    val id: UUID
    val name: String
    val description: String
    val pictures: List<String>
    val categories: List<Category>
}

data class Product(
    override val id: UUID,
    override val name: String,
    override val description: String,
    override val pictures: List<String>,
    override val categories: List<Category>
): IProduct

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
