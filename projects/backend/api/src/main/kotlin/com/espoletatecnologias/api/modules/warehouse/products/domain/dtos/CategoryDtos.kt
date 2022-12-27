@file:UseSerializers(UUIDSerializer::class)

package com.espoletatecnologias.api.modules.warehouse.products.domain.dtos


import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Category
import com.espoletatecnologias.common.clean.crud.InputDto
import com.espoletatecnologias.common.clean.crud.OutputDto
import com.espoletatecnologias.common.clean.crud.UpdateDto
import com.espoletatecnologias.common.framework.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*


@Serializable
data class ReadCategoryDto(
    val id: UUID,
    val name: String,
    val description: String,
    val parent: ReadCategoryDto?,
    val parentId: UUID?
) {
    companion object : OutputDto<ReadCategoryDto, Category> {
        override fun fromEntity(entity: Category): ReadCategoryDto {
            return ReadCategoryDto(
                id = entity.id,

                name = entity.name,

                description = entity.description,

                parent = if (entity.parent != null) fromEntity(
                    entity.parent
                ) else null,
                parentId = entity.parentId
            )
        }

    }
}

@Serializable
data class CreateCategoryDto(
    val name: String,
    val description: String,
    val parentId: UUID? = null
) : InputDto<Category> {
    override fun toEntity(): Category {
        return Category(
            name = name,
            description = description,
            parent = null,
            parentId = parentId,
            id = UUID.randomUUID()
        )
    }
}

@Serializable
data class UpdateCategoryDto(
    val name: String? = null,
    val description: String? = null,
    val parentId: UUID? = null,
    override val id: UUID
) : UpdateDto<Category> {
    override fun toEntity(toUpdate: Category): Category {
        return toUpdate.copy(
            name = name ?: toUpdate.name,
            description = description ?: toUpdate.description,
            parentId = parentId ?: toUpdate.parentId
        )
    }
}
