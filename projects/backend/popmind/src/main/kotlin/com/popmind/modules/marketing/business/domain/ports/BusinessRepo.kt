package com.popmind.modules.marketing.business.domain.ports

import com.espoletatecnologias.common.clean.crud.CrudRepository
import com.popmind.modules.marketing.business.domain.models.Business

interface BusinessRepo: CrudRepository<Business>
