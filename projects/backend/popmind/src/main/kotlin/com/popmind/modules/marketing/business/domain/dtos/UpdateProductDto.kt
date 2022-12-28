@file:UseSerializers(UUIDSerializer::class)

package com.popmind.modules.marketing.business.domain.dtos

import com.espoletatecnologias.common.framework.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

@Serializable
data class UpdateProductDto(
    val id: UUID? = null
)
