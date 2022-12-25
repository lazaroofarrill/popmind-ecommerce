package com.espoletatecnologias.api.framework.clean.crud

interface Dto<TEntity> {
    fun validate(): Boolean = true

    fun toEntity(): TEntity
}
