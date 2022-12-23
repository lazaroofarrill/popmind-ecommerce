package com.espoletatecnologias.api.modules.warehouse.models

import java.util.*

data class Warehouse(val id: UUID, val name: String, val parent: Warehouse?)
