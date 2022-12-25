package com.espoletatecnologias.api.clean.crud

interface Dto<TEntity> {
    fun validate(): Boolean = true

    fun toEntity(): TEntity
}
