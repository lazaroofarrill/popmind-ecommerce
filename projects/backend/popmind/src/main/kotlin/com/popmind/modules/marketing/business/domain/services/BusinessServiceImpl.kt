package com.popmind.modules.marketing.business.domain.services

import com.popmind.modules.marketing.business.domain.dtos.UpdateBusinessDto
import com.popmind.modules.marketing.business.domain.models.Business
import com.popmind.modules.marketing.business.domain.ports.BusinessRepo
import com.popmind.modules.marketing.business.domain.ports.BusinessService
import com.popmind.modules.marketing.business.domain.ports.CategoryService
import com.popmind.modules.marketing.business.domain.ports.MenuService
import io.ktor.server.plugins.*
import org.koin.core.component.getScopeName
import java.util.*

class BusinessServiceImpl(
    private val businessRepo: BusinessRepo,
    private val categoryService: CategoryService,
    private val menuService: MenuService
) : BusinessService {
    override suspend fun createBusiness(businessDto: UpdateBusinessDto): Business {
        validateBusinessCreateDto(businessDto)

        var newBusiness = Business(
            id = UUID.randomUUID(),
            name = businessDto.name ?: throw Error(),
            description = businessDto.description ?: throw Error(),
            categories = businessDto.categoriesIds?.let {
                categoryService.findById(businessDto.categoriesIds)
            } ?: throw Error(),
            menus = emptyList()
        )

        newBusiness = businessRepo.create(newBusiness)

        businessDto.menus?.let { updateMenuDtos ->
            val menusToCreate = updateMenuDtos.map { it ->
                it.copy(
                    businessId = newBusiness.id
                )
            }
            menuService.create(menusToCreate)
        }

        return newBusiness
    }

    private fun validateBusinessCreateDto(updateBusinessDto: UpdateBusinessDto) {
        updateBusinessDto.id?.let {
            throw BadRequestException("UUID must not be defined when creating a business")
        }
        updateBusinessDto.name?.minLength(3)
            ?: throw BadRequestException("name is required")
        updateBusinessDto.categoriesIds?.minLength(1)
    }
}

fun String.minLength(length: Int): String {
    if (this.length < length) {
        throw BadRequestException("${this::getScopeName.name} length is $length")
    }
    return this
}

fun <T> List<T>.minLength(length: Int): List<T> {
    return if (this.size < length) {
        throw Error("Array ${this::getScopeName.name} minimum length of $length")
    } else this
}
