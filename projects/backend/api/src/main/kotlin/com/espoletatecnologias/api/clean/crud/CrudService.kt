package com.espoletatecnologias.api.clean.crud

import java.util.*

abstract class CrudService<T : Any, TCreateDto : Dto<T>,
        TUpdateDto : Dto<T>>(private val repo: CrudRepository<T>) {


    suspend fun find(): List<T> {
        return repo.find()
    }

    suspend fun findOne(id: UUID): T? = repo.findOne(id)

    suspend fun create(createDto: TCreateDto): T {
        createDto.validate()
        val newProduct = createDto.toEntity()
        return repo.create(newProduct)
    }

    suspend fun update(updateDto: TUpdateDto): T {
        updateDto.validate()
        return repo.update(updateDto.toEntity())
    }

    suspend fun delete(id: UUID): Boolean {
        return repo.delete(id)
    }
}
