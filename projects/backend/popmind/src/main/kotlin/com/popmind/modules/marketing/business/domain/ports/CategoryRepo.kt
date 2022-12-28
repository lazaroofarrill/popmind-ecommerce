package com.popmind.modules.marketing.business.domain.ports

import com.espoletatecnologias.common.clean.crud.CrudRepository
import com.popmind.modules.marketing.business.domain.models.Category

interface CategoryRepo: CrudRepository<Category>
