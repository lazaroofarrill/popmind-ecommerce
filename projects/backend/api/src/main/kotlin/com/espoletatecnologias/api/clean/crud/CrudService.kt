package com.espoletatecnologias.api.clean.crud

import kotlinx.css.option
import java.util.*

abstract class CrudService<T : Any, TCreateDto : InputDto<T>,
        TUpdateDto : InputDto<T>>(private val repo: CrudRepository<T>) {


    suspend fun find(options: FindManyOptions): FindManyResponse<T> {
        return repo.find(options)
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
