package com.espoletatecnologias.api.modules.warehouse.products.domain.models

import com.espoletatecnologias.common.clean.crud.BaseEntity
import java.util.*


data class Category(
    override val id: UUID,
    val name: String,
    val description: String,
    val parent: Category?,
    val parentId: UUID? = null
): BaseEntity
