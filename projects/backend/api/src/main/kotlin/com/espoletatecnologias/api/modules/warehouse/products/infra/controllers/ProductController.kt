package com.espoletatecnologias.api.modules.warehouse.products.infra.controllers

import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.framework.types.Router
import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.CreateProductDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.services.ProductService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class ProductController(private val productService: ProductService) :
    Controller {
    override val router: Router = {
        route("warehouse/products") {
            get {
                call.respondText {
                    productService.find().toString()
                }
            }

            post {
                val createProductDto = call.receive<CreateProductDto>()

                val createdProduct = productService.create(createProductDto)
                call.respondText {
                    createdProduct.toString()
                }
            }
        }

    }
}
