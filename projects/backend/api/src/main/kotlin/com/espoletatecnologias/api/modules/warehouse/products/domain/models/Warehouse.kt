package com.espoletatecnologias.api.modules.warehouse.products.domain.models

import java.util.*

data class Warehouse(val id: UUID, val name: String, val parent: Warehouse?)
