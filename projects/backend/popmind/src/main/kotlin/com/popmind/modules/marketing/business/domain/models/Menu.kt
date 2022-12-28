package com.popmind.modules.marketing.business.domain.models

import com.espoletatecnologias.common.clean.crud.BaseEntity
import java.util.*

data class Menu(
    override val id: UUID,
    val businessId: UUID
) : BaseEntity
