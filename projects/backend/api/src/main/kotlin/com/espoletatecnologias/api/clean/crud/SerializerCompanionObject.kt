package com.espoletatecnologias.api.clean.crud

interface SerializerCompanionObject<TDto, TEntity> {
    fun fromEntity(entity: TEntity): TDto
}
