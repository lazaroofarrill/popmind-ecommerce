package com.espoletatecnologias.api.modules.warehouse.products.domain.models

import java.util.*

interface BaseEntity {
    val id: UUID
}

interface IProduct : BaseEntity {
    val name: String
    val description: String
    val pictures: List<String>
    val categories: List<Category>
}

interface IProductVariant : BaseEntity {
    val parentId: UUID
    val properties: Map<VariantProperty, String>
    val pictures: List<String>
}

data class Product(
    override val id: UUID,
    override val name: String,
    override val description: String,
    override val pictures: List<String>,
    override val categories: List<Category>
) : IProduct

data class ProductVariant(
    override val id: UUID,
    override val parentId: UUID,
    override val properties: Map<VariantProperty, String>,
    override val pictures: List<String>
) : IProductVariant {
}

data class VariantProperty(
    val id: UUID,
    val name: String,
    val allowedValues: List<String>
)
