package com.popmind.modules.marketing.business.domain.models

import com.espoletatecnologias.common.clean.crud.BaseEntity
import java.util.*

data class Category(
    override val id: UUID,
    val name: String,
    val icon: String
) : BaseEntity
