package com.espoletatecnologias.api.clean.crud

import java.util.*

interface CrudRepository<T : Any> {
    suspend fun find(options: FindManyOptions = FindManyOptions()): FindManyResponse<T>

    suspend fun findOne(id: UUID): T?

    suspend fun findOne(options: FindOptions = FindOptions()): T?

    suspend fun create(newProduct: T): T

    suspend fun update(updatedProduct: T): T

    suspend fun delete(id: UUID): Boolean

    suspend fun delete(ids: List<UUID>): Boolean
}

data class FindOptions(
    val select: Map<Any, Any> = mapOf(),
    val where: Map<Any, Any> = mapOf()
)

data class FindManyOptions(
    val limit: Int = 100,
    val offset: Long = 0,
    val select: Map<Any, Any> = mapOf(),
    val where: Map<Any, Any> = mapOf()
)
