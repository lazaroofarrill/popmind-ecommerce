package com.espoletatecnologias.api.modules.warehouse.products.domain.dtos

import com.espoletatecnologias.api.clean.crud.InputDto
import com.espoletatecnologias.api.clean.crud.OutputDto
import com.espoletatecnologias.api.clean.crud.UpdateDto
import com.espoletatecnologias.api.framework.serializers.UUIDSerializer
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Category
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ReadCategoryDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,

    val name: String,

    val description: String,

    val parent: ReadCategoryDto?
) {
    companion object : OutputDto<ReadCategoryDto, Category> {
        override fun fromEntity(entity: Category): ReadCategoryDto {
            return ReadCategoryDto(
                id = entity.id,

                name = entity.name,

                description = entity.description,

                parent = if (entity.parent != null) fromEntity(
                    entity.parent
                ) else null
            )
        }

    }
}

@Serializable
data class CreateCategoryDto(
    val name: String,
    val description: String
) : InputDto<Category> {
    override fun toEntity(): Category {
        return Category(
            name = name,
            description = description,
            parent = null,
            id = UUID.randomUUID()
        )
    }
}

@Serializable
data class UpdateCategoryDto(
    val name: String? = null,

    val description: String? = null,

    @Serializable(with = UUIDSerializer::class)
    override val id: UUID
) : UpdateDto<Category> {
    override fun toEntity(toUpdate: Category): Category {
        return toUpdate.copy(
            name = name ?: toUpdate.name,
            description = description ?: toUpdate.description
        )
    }
}
