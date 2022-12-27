package com.espoletatecnologias.common.modules.details.database.services

import com.espoletatecnologias.common.clean.crud.UnitOfWorkService
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransaction

class ExposedUnitOfWorkService(private val database: Database) :
    UnitOfWorkService {
    override suspend fun <T> exec(detach: Boolean, block: suspend () -> T): T {
        return if (detach) {
            newSuspendedTransaction(Dispatchers.IO, database) {
                block()
            }
        } else {
            TransactionManager.currentOrNull()?.let {
                it.suspendedTransaction {
                    block()
                }
            } ?: newSuspendedTransaction(Dispatchers.IO, database) {
                block()
            }
        }
    }
}


