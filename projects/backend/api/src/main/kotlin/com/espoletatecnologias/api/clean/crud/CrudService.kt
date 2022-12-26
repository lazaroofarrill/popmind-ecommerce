package com.espoletatecnologias.api.clean.crud

import java.util.*

abstract class CrudService<T : Any, TCreateDto : InputDto<T>,
        TUpdateDto : InputDto<T>>(
    private val repo: CrudRepository<T>,
    private val uwo: UnitOfWorkService
) {


    suspend fun find(options: FindManyOptions): FindManyResponse<T> {
        return uwo.exec { repo.find(options) }
    }


    suspend fun findOne(id: UUID): T? {
        return uwo.exec { repo.findOne(id) }
    }

    suspend fun create(createDto: TCreateDto): T {
        return uwo.exec {
            createDto.validate()
            val newProduct = createDto.toEntity()
            repo.create(newProduct)
        }
    }


    suspend fun update(updateDto: TUpdateDto): T {
        return uwo.exec {
            updateDto.validate()
            repo.update(updateDto.toEntity())
        }
    }

    suspend fun delete(id: UUID): Boolean {
        return uwo.exec {
            repo.delete(id)
        }
    }
}
