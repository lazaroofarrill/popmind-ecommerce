package com.espoletatecnologias.common.clean.crud

interface InputDto<TEntity> {
    fun validate(): Boolean = true

    fun toEntity(): TEntity
}

interface UpdateDto<TEntity>: BaseEntity {
    fun validate(): Boolean = true

    fun toEntity(toUpdate: TEntity): TEntity
}
