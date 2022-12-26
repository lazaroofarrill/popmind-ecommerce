package com.espoletatecnologias.api.clean.crud

interface UnitOfWorkService {
    suspend fun <T> exec(detach: Boolean = false, block: suspend () -> T): T
}
