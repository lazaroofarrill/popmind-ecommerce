package com.popmind.modules.marketing.business.domain.models

import com.espoletatecnologias.common.clean.crud.BaseEntity
import java.util.*

data class Business(
    override val id: UUID,
    val name: String,
    val description: String,
    val categories: List<Category>,
    val menus: List<Menu>
) : BaseEntity
