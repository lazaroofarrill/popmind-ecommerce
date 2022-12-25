package com.espoletatecnologias.api.clean.crud

typealias UnitOfWork = suspend (block: suspend () -> Any) -> Any
