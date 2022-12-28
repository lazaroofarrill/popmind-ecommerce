@file:UseSerializers(UUIDSerializer::class)

package com.popmind.modules.marketing.business.domain.dtos

import com.espoletatecnologias.common.framework.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

@Serializable
data class UpdateMenuDto(
    val id: UUID? = null,
    val products: List<UpdateProductDto>,
    val businessId: UUID
)
