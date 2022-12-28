package com.popmind.modules.marketing.business.domain.ports

import com.popmind.modules.marketing.business.domain.dtos.UpdateMenuDto
import com.popmind.modules.marketing.business.domain.models.Menu

interface MenuService {
    fun create(updateMenuDtos: List<UpdateMenuDto>): List<Menu>
}
