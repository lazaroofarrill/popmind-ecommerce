package com.espoletatecnologias.api.modules.details.database

import com.espoletatecnologias.api.clean.crud.UnitOfWork
import com.espoletatecnologias.api.framework.arch.ApplicationModule
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.reflect.KClass

private val dbQuery: UnitOfWork = {
    newSuspendedTransaction(Dispatchers.IO) {
        it()
    }
}

class ExposedDatabaseModule : ApplicationModule() {
    override val module: Module = module {
        single { dbQuery }
    }

    companion object {
        fun bootstrap(schemas: List<Table>): KClass<ExposedDatabaseModule> {
            val driverClassName = "org.h2.Driver"
            val jdbcUrl = "jdbc:h2:file:./build/db"
            val database =
                Database.connect(driver = driverClassName, url = jdbcUrl)

            transaction(database) {
                schemas.forEach {
                    SchemaUtils.create(it)
                }
            }

            return ExposedDatabaseModule::class
        }
    }
}


