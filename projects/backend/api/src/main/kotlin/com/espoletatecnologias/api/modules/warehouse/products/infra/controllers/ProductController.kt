package com.espoletatecnologias.api.modules.warehouse.products.infra.controllers

import com.espoletatecnologias.api.clean.crud.UnitOfWork
import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.framework.common.exceptions.DalInsertError
import com.espoletatecnologias.api.framework.types.Router
import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.ProductDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.services.ProductService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


class ProductController(
    private val productService: ProductService,
    private val unitOfWork: UnitOfWork
) :
    Controller {
    override val router: Router = {
        route("warehouse/products") {
            get {
                unitOfWork {

                    call.respond(productService.find().map {
                        ProductDto.fromEntity(it)
                    })
                }
            }

            post {
                unitOfWork {
                    val createProductDto = call.receive<ProductDto>()

                    for (i in 1..10000) {
                        productService.create(createProductDto.copy(description = createProductDto.description + "lol"))
                    }
                    val createdProduct = productService.create(createProductDto)

                    throw DalInsertError()
                    call.respond(ProductDto.fromEntity(createdProduct))
                }
            }
        }

    }
}
