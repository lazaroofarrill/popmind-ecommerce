package com.espoletatecnologias.api.clean.crud

interface InputDto<TEntity> {
    fun validate(): Boolean = true

    fun toEntity(): TEntity
}
