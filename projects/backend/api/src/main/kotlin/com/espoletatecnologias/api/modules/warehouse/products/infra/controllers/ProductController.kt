package com.espoletatecnologias.api.modules.warehouse.products.infra.controllers

import com.espoletatecnologias.api.clean.crud.FindManyResponse
import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.framework.common.utils.extractFindOptions
import com.espoletatecnologias.api.framework.common.utils.setFindMeta
import com.espoletatecnologias.api.framework.types.Router
import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.CreateProductDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.ReadProductDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.dtos.UpdateProductDto
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import com.espoletatecnologias.api.modules.warehouse.products.domain.services.ProductService
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*


class ProductController(
    private val productService: ProductService,
) : Controller {
    private val getProducts: Router = {
        get {
            val products: FindManyResponse<Product> =
                productService.find(extractFindOptions())

            setFindMeta(products)

            call.respond(products.items.map {
                ReadProductDto.fromEntity(it)
            })
        }
    }

    private val createProduct: Router = {
        post {
            val createProductDto = call.receive<CreateProductDto>()
            val createdProduct = productService.create(createProductDto)

            call.respond(ReadProductDto.fromEntity(createdProduct))
        }
    }

    private val updateProduct: Router = {
        patch {
            val updateDto = call.receive<UpdateProductDto>()
            val response = productService.update(updateDto)
            call.respond(ReadProductDto.fromEntity(response))
        }
    }

    private val deleteProduct: Router = {
        delete("{id}") {
            val productToDeleteId = call.parameters["id"]
                ?: throw BadRequestException("Parameter not present")
            val deleteResult =
                productService.delete(UUID.fromString(productToDeleteId))

            if (deleteResult) {
                call.respond("OK")
            } else {
                throw Error("Error deleting resource")
            }
        }
    }

    private val getProductsUi: Router = {
        get("ui") {
            val productFindManyResponse =
                productService.find(extractFindOptions())
            setFindMeta(productFindManyResponse)
            call.respond(
                FreeMarkerContent(
                    "warehouse/products/products-list.ftl",
                    mapOf("products" to productFindManyResponse.items)
                )
            )
        }
    }

    private val createProductVariant: Router = {
        post {

        }
    }

    private val getProductVariants: Router = {
        get {

        }
    }

    override val router: Router = {
        route("warehouse/products") {
            getProducts()
            createProduct()
            updateProduct()
            deleteProduct()
            getProductsUi()

            route("variants") {
                createProductVariant()
                getProductVariants()
            }
        }
    }
}
