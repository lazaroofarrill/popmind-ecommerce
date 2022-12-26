package com.espoletatecnologias.api.modules.details.database

import com.espoletatecnologias.api.clean.crud.UnitOfWorkService
import com.espoletatecnologias.api.framework.arch.ApplicationModule
import com.espoletatecnologias.api.modules.details.database.services.ExposedUnitOfWorkService
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.reflect.KClass

private var database: Database? = null

class ExposedDatabaseModule : ApplicationModule() {
    override val module: Module = module {
        factory<UnitOfWorkService> {
            ExposedUnitOfWorkService(
                database ?: throw Error(
                    "Module has not been correctly initialized." +
                            "Try calling the bootstrap method"
                )
            )
        }
    }

    companion object {
        fun bootstrap(schemas: List<Table>): KClass<ExposedDatabaseModule> {
            val driverClassName = "org.h2.Driver"
            val jdbcUrl = "jdbc:h2:file:./build/db"
            database =
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


