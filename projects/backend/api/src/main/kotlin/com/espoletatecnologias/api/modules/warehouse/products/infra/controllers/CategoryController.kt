package com.espoletatecnologias.api.modules.warehouse.products.infra.controllers

import com.espoletatecnologias.common.framework.arch.Controller

import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.CreateCategoryDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.ReadCategoryDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.UpdateCategoryDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.services.CategoryService
import com.espoletatecnologias.common.framework.common.utils.extractFindOptions
import com.espoletatecnologias.common.framework.common.utils.extractUUID
import com.espoletatecnologias.common.framework.common.utils.setFindMeta
import com.espoletatecnologias.common.framework.types.Router
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class CategoryController(private val categoryService: CategoryService) :
    Controller {
    private val listCategories: Router = {
        get {
            val response = categoryService.find(extractFindOptions())
            setFindMeta(response)
            val responseData = response.items.map {
                ReadCategoryDto.fromEntity(it)
            }
            call.respond(responseData)
        }
    }

    private val getSingleCategory: Router = {
        get("{id}") {
            val id = extractUUID()
            val foundCategory = categoryService.findOne(id)
                ?: throw NotFoundException("Entity not found")
            call.respond(ReadCategoryDto.fromEntity(foundCategory))
        }
    }

    private val createCategory: Router = {
        post {
            val createCategoryDto = call.receive<CreateCategoryDto>()
            val createResult = categoryService.create(createCategoryDto)
            call.respond(ReadCategoryDto.fromEntity(createResult))
        }
    }

    private val updateCategory: Router = {
        patch {
            val updateCategoryDto = call.receive<UpdateCategoryDto>()
            val updatedCategory = categoryService.update(updateCategoryDto)
            call.respond(ReadCategoryDto.fromEntity(updatedCategory))
        }
    }

    private val deleteCategory: Router = {
        delete("{id}") {
            val idToDelete = extractUUID()
            val result = categoryService.delete(idToDelete)
            if (result) {
                call.respond("OK")
            } else {
                throw Error("Error deleting category")
            }
        }
    }

    override val router: Router = {
        route("warehouse/categories") {
            listCategories()
            getSingleCategory()
            createCategory()
            updateCategory()
            deleteCategory()
        }
    }
}
