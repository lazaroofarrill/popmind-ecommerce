@file:UseSerializers(UUIDSerializer::class)

package com.popmind.modules.marketing.business.domain.dtos

import com.espoletatecnologias.common.framework.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

@Serializable
data class UpdateBusinessDto(
    val id: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    val categoriesIds: List<UUID>? = null,
    val menus: List<UpdateMenuDto>? = null
)
