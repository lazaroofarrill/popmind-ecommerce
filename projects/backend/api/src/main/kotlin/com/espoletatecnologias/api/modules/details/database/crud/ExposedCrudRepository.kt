package com.espoletatecnologias.api.modules.details.database.crud

import com.espoletatecnologias.api.clean.crud.*
import com.espoletatecnologias.api.framework.common.exceptions.DalInsertError
import com.espoletatecnologias.api.framework.common.exceptions.DalUpdateError
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import java.util.*
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

open class CommonTable(name: String = "") : Table(name) {
    val id = uuid("id").autoGenerate()
    override val primaryKey = PrimaryKey(id)
}

@Suppress("UNCHECKED_CAST")
open class ExposedCrudRepository<T : BaseEntity>(
    private val table: CommonTable,
    private val resultRowToEntity: ResultRow.() -> T
) :
    CrudRepository<T> {
    override suspend fun find(options: FindManyOptions): FindManyResponse<T> {
        val query = commonSelectQuery()

        applyFiltersToQuery(query, options.where)

        val totalCount = query.count()

        query.limit(options.limit, options.offset)

        val queryCount = query.count()

        val items = query.map(resultRowToEntity)

        return FindManyResponse(
            items = items,
            limit = options.limit,
            count = queryCount,
            offset = options.offset,
            total = totalCount
        )
    }

    override suspend fun findOne(id: UUID): T? {
        val query =
            commonSelectQuery()
                .andWhere {
                    table.id eq id
                }

        return query.singleOrNull()?.let(resultRowToEntity)
    }

    override suspend fun findOne(options: FindOptions): T? {
        val query = commonSelectQuery()

        applyFiltersToQuery(query, options.where)

        return query.singleOrNull()?.let(resultRowToEntity)
    }

    override suspend fun delete(id: UUID): Boolean {
        return table.deleteWhere { table.id eq id } > 0

    }

    override suspend fun delete(ids: List<UUID>): Boolean {
        return table.deleteWhere {
            id inList (ids)
        } == ids.size
    }

    protected fun applyInserts(
        insertStatement: UpdateBuilder<Int>,
        newRecord: T
    ) {
        val columnsToUpdate =
            table::class.memberProperties.filter { column ->
                column.isAccessible = true
                column.getter.call(table) is Column<*>
            }.map { prop ->
                prop.getter.call(table) as Column<*>
            }

        val insertValues: List<Pair<Column<*>, KProperty1<out T, *>>> =
            newRecord::class.declaredMemberProperties.map { newRecordProp ->
                val columnMatch = columnsToUpdate.find { column ->
                    column.name == newRecordProp.name
                }
                if (columnMatch == null) return@map null
                else Pair(columnMatch, newRecordProp)
            }.filterNotNull()

        insertValues.forEach { pair ->
            println(
                "To insert: ${pair.first.name} --- ${
                    pair.second.getter.call(
                        newRecord
                    )
                }"
            )
            when (val newRecordValue = pair.second.getter.call(newRecord)) {
                is String -> insertStatement[pair.first as Column<String>] =
                    newRecordValue

                is Int -> insertStatement[pair.first as Column<Int>] =
                    newRecordValue

                is Long -> insertStatement[pair.first as Column<Long>] =
                    newRecordValue

                is Enum<*> -> insertStatement[pair.first as Column<Enum<*>>] =
                    newRecordValue

                is UUID -> insertStatement[pair.first as Column<UUID>] =
                    newRecordValue

                is List<*> -> insertStatement[pair.first as Column<String>] =
                    Json.encodeToString(newRecordValue as List<String>)

                else -> throw Error("no map for value ${pair.second.name}")
            }
        }
    }

    override suspend fun update(updatedRecord: T): T {
        table.update({
            table.id eq updatedRecord.id
        }) {
            applyInserts(it, updatedRecord)
        }
        return this.findOne(updatedRecord.id) ?: throw DalUpdateError()
    }

    protected open val insertOverride: InsertStatement<Number>.() -> Unit = {}

    override suspend fun create(newRecord: T): T {
        val insertStatement = table.insert { insertStatement ->

            applyInserts(insertStatement, newRecord)

            insertOverride(insertStatement)
        }

        return insertStatement.resultedValues?.singleOrNull()
            ?.let(resultRowToEntity)
            ?: throw DalInsertError()
    }

    protected open fun commonSelectQuery(): Query {
        return table.selectAll()
    }

    private fun applyFiltersToQuery(
        query: Query,
        where: Map<Any, Any>
    ): Query {
        where.forEach { filter ->
            if (filter.key is String) {
                val column: Column<*> =
                    table::class.memberProperties.find {
                        it.name == filter.key
                    }?.let {
                        it.isAccessible = true
                        val colProp = it.getter.call(table)
                        if (colProp is Column<*>) {
                            colProp
                        } else {
                            throw Error("Property is not of type column")
                        }
                    }
                        ?: throw Error("Column for filtering ${filter.key} not found")

                query.andWhere {
                    column as Column<String> eq filter.value as String
                }
            } else {
                throw Error("This repository only handles string keys")
            }

        }

        return query
    }

}
