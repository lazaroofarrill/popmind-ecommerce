package com.espoletatecnologias.api.modules.warehouse.products.domain.models

import com.espoletatecnologias.api.clean.crud.BaseEntity
import java.util.*


data class Category(
    override val id: UUID,
    val name: String,
    val description: String,
    val parent: Category?
): BaseEntity
