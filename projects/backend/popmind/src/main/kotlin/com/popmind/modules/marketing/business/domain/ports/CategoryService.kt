package com.popmind.modules.marketing.business.domain.ports

import com.espoletatecnologias.common.clean.crud.FindOptions
import com.popmind.modules.marketing.business.domain.models.Category
import java.util.*

interface CategoryService {
    fun find(options: FindOptions): List<Category>
    fun findById(categoriesIds: List<UUID>): List<Category>
}
