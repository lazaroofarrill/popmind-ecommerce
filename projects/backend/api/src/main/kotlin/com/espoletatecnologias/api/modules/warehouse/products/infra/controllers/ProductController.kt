package com.espoletatecnologias.api.modules.warehouse.products.infra.controllers

import com.espoletatecnologias.api.clean.crud.FindManyResponse
import com.espoletatecnologias.api.clean.crud.UnitOfWork
import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.framework.common.utils.extractFindOptions
import com.espoletatecnologias.api.framework.common.utils.setFindMeta
import com.espoletatecnologias.api.framework.types.Router
import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.CreateProductDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.ReadProductDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
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
                    val products: FindManyResponse<Product> =
                        productService.find(extractFindOptions())

                    setFindMeta(products)

                    call.respond(products.items.map {
                        ReadProductDto.fromEntity(it)
                    })
                }
            }

            post {
                unitOfWork {
                    val createProductDto = call.receive<CreateProductDto>()
                    val createdProduct = productService.create(createProductDto)

                    call.respond(ReadProductDto.fromEntity(createdProduct))
                }
            }
        }

    }
}
