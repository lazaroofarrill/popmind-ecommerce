package com.espoletatecnologias.api.modules.warehouse.products.domain.dtos

import com.espoletatecnologias.api.clean.crud.Dto
import com.espoletatecnologias.api.clean.crud.SerializerCompanionObject
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import kotlinx.serialization.Serializable
import java.util.*


@Serializable()
data class ProductDto(
    val name: String,
    val description: String
) : Dto<Product> {
    override fun toEntity(): Product {
        return Product(
            id = UUID.randomUUID(),
            name = name,
            description = description,
            pictures = emptyList(),
            categories = emptyList()
        )
    }

    companion object : SerializerCompanionObject<ProductDto, Product> {
        override fun fromEntity(entity: Product): ProductDto {
            return ProductDto(
                entity.name,
                entity.description
            )
        }
    }
}
