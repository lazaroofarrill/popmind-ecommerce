package com.espoletatecnologias.api.modules.warehouse.products.domain.dtos

import com.espoletatecnologias.api.clean.crud.InputDto
import com.espoletatecnologias.api.clean.crud.OutputDto
import com.espoletatecnologias.api.framework.serializers.UUIDSerializer
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Category
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.IProduct
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.util.*


@Serializable()
data class CreateProductDto(
    override val name: String,

    override val description: String,

    @Transient
    override val id: UUID = UUID.randomUUID(),

    override val pictures: List<String>,

    @Transient
    override val categories: List<Category> = emptyList()
) : IProduct, InputDto<Product> {
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
    @Serializable(with = UUIDSerializer::class)
    override val id: UUID,

    override val name: String,

    override val description: String,

    override val pictures: List<String>,

    @Transient
    override val categories: List<@Contextual Category> = emptyList()
) : IProduct {

    companion object : OutputDto<ReadProductDto, Product> {
        override fun fromEntity(entity: Product): ReadProductDto {
            return ReadProductDto(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                pictures = entity.pictures,
                categories = entity.categories
            )
        }

    }

}
