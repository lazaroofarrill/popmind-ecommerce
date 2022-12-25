package com.espoletatecnologias.api.clean.crud

import java.util.*

interface CrudRepository<T : Any> {
    suspend fun find(): List<T>

    suspend fun findOne(id: UUID): T?

    suspend fun create(newProduct: T): T

    suspend fun update(updatedProduct: T): T

    suspend fun delete(id: UUID): Boolean

    suspend fun delete(ids: List<UUID>): Boolean
}
