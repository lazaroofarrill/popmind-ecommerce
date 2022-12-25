package com.espoletatecnologias.api.clean.crud

interface OutputDto<TDto, TEntity> {
    fun fromEntity(entity: TEntity): TDto
}
