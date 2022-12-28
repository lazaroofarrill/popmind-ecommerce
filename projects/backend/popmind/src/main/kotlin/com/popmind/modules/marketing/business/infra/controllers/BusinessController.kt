package com.popmind.modules.marketing.business.infra.controllers

import com.espoletatecnologias.common.framework.arch.Controller
import com.espoletatecnologias.common.framework.common.utils.extractUUID
import com.espoletatecnologias.common.framework.types.Router
import com.popmind.modules.marketing.business.domain.ports.BusinessService
import io.ktor.server.routing.*

class BusinessController(private val businessService: BusinessService) : Controller {
    private val listBusinesses: Router = {

    }

    private val saveBusiness: Router = {
        put {

        }
    }

    private val deleteBusiness: Router = {
        delete("{id}") {
            val id = extractUUID()
        }
    }

    override val router: Router = {
        listBusinesses()
        saveBusiness()
        deleteBusiness()
    }
}
