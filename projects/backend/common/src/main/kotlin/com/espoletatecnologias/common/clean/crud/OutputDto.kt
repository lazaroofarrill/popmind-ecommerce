package com.espoletatecnologias.common.clean.crud

interface OutputDto<TDto, TEntity> {
    fun fromEntity(entity: TEntity): TDto
}
