package com.espoletatecnologias.api.modules.warehouse.products.domain.dtos


import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import com.espoletatecnologias.common.clean.crud.InputDto
import com.espoletatecnologias.common.clean.crud.OutputDto
import com.espoletatecnologias.common.clean.crud.UpdateDto
import com.espoletatecnologias.common.framework.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*


@Serializable()
data class CreateProductDto(
    val name: String,

    val description: String,


    val pictures: List<String>,

    ) : InputDto<Product> {
    override fun toEntity(): Product {
        return Product(
            id = UUID.randomUUID(),
            name = name,
            description = description,
            pictures = pictures,
        )
    }

    val categoryIds:
            List<@Serializable(with = UUIDSerializer::class) UUID>? = null
}

@Serializable
data class ReadProductDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID,

    val name: String,

    val description: String,

    val pictures: List<String>,

    val categories: List<ReadCategoryDto>
) {
    companion object : OutputDto<ReadProductDto, Product> {
        override fun fromEntity(entity: Product): ReadProductDto {
            return ReadProductDto(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                pictures = entity.pictures,
                categories = entity.categories?.map {
                    ReadCategoryDto.fromEntity(
                        it
                    )
                } ?: emptyList()
            )
        }
    }
}

@Serializable
data class UpdateProductDto(
    @Serializable(with = UUIDSerializer::class)
    override val id: UUID,

    val name: String? = null,

    val description: String? = null,

    val pictures: List<String>? = null,

    val categoryIds: List<@Serializable(with = UUIDSerializer::class) UUID>? = null
) : UpdateDto<Product> {
    override fun toEntity(toUpdate: Product): Product {
        return toUpdate.copy(
            name = name ?: toUpdate.name,
            description = description ?: toUpdate.description,
            pictures = pictures ?: toUpdate.pictures
        )
    }
}
