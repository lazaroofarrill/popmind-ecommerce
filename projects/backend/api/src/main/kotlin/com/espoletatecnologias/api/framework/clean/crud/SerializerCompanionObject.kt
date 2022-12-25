package com.espoletatecnologias.api.framework.clean.crud

interface SerializerCompanionObject<TDto, TEntity> {
    fun fromEntity(entity: TEntity): TDto
}
