package com.popmind.modules.marketing.business.domain.ports

import com.popmind.modules.marketing.business.domain.dtos.UpdateBusinessDto
import com.popmind.modules.marketing.business.domain.models.Business

interface BusinessService {
    suspend fun createBusiness(businessDto: UpdateBusinessDto): Business
}
