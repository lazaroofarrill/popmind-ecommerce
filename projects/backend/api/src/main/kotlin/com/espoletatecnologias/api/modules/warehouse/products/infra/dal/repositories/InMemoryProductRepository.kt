package com.espoletatecnologias.api.modules.warehouse.products.infra.dal.repositories

import com.espoletatecnologias.api.clean.crud.FindManyOptions
import com.espoletatecnologias.api.clean.crud.FindManyResponse
import com.espoletatecnologias.api.clean.crud.FindOptions
import com.espoletatecnologias.api.framework.common.exceptions.DalInsertError
import com.espoletatecnologias.api.framework.common.exceptions.DalUpdateError
import com.espoletatecnologias.api.modules.warehouse.products.domain.ports.ProductRepository
import com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product
import java.util.*

private val products: MutableList<Product> = mutableListOf()

