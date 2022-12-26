package com.espoletatecnologias.api.modules.warehouse.products.domain.dtos

import com.espoletatecnologias.api.clean.crud.OutputDto
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
