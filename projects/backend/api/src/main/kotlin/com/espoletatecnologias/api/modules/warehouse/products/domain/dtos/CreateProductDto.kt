package com.espoletatecnologias.api.modules.warehouse.products.domain.dtos

import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import com.espoletatecnologias.api.framework.clean.crud.Dto
import kotlinx.serialization.Serializable
import java.util.*


@Serializable()
data class CreateProductDto(
    val name: String,
    val description: String
) : Dto<Product> {
    override fun toEntity(): Product {
        return Product(
            id = UUID.randomUUID(),
            name = name,
            description = description,
            pictures = emptyList(),
            rating = 1.0f,
            categories = emptyList()
        )
    }
}

