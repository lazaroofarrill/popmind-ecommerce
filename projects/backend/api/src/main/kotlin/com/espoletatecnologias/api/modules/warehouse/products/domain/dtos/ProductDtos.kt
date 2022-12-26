package com.espoletatecnologias.api.modules.warehouse.products.domain.dtos

import com.espoletatecnologias.api.clean.crud.InputDto
import com.espoletatecnologias.api.clean.crud.OutputDto
import com.espoletatecnologias.api.framework.serializers.UUIDSerializer
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Category
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.util.*


@Serializable()
data class CreateProductDto(
    val name: String,

    val description: String,

    @Transient val id: UUID = UUID.randomUUID(),

    val pictures: List<String>,

    @Transient val categories: List<Category> = emptyList()
) : InputDto<Product> {
    override fun toEntity(): Product {
        return Product(
            id = UUID.randomUUID(),
            name = name,
            description = description,
            pictures = pictures,
            categories = emptyList()
        )
    }
}

@Serializable
data class ReadProductDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID,

    val name: String,

    val description: String,

    val pictures: List<String>,

    val categories: List<ReadCategoryDto> = emptyList()
) {

    companion object : OutputDto<ReadProductDto, Product> {
        override fun fromEntity(entity: Product): ReadProductDto {
            return ReadProductDto(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                pictures = entity.pictures,
                categories = entity.categories.map {
                    ReadCategoryDto.fromEntity(
                        it
                    )
                }
            )
        }
    }
}
