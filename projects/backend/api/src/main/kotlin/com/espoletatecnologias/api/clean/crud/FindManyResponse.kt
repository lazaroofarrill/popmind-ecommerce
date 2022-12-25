package com.espoletatecnologias.api.clean.crud

data class FindManyResponse<T>(
    val limit: Int,
    val offset: Long,
    val count: Long,
    val total: Long,
    val items: List<T>
)
