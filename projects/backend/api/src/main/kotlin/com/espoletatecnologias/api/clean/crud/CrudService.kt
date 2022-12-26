package com.espoletatecnologias.api.clean.crud

import com.espoletatecnologias.api.framework.common.exceptions.DalUpdateError
import java.util.*

@Suppress("MemberVisibilityCanBePrivate")
abstract class CrudService<
        T : Any,
        TCreateDto : InputDto<T>,
        TUpdateDto : UpdateDto<T>>(
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
            val toUpdate = findOne(updateDto.id)
                ?: throw DalUpdateError("RecordShouldExist")
            repo.update(updateDto.toEntity(toUpdate))
        }
    }

    suspend fun delete(id: UUID): Boolean {
        return uwo.exec {
            repo.delete(id)
        }
    }
}
