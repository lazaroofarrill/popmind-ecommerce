package com.espoletatecnologias.api.modules.warehouse.products.domain.models

import java.util.*

data class Category(val id: UUID, val name: String, val parent: Category?)
