@file:Suppress("unused")

package com.espoletatecnologias.api.modules.warehouse.products.domain.models

import java.time.ZonedDateTime
import java.util.*

data class Stock(
    val id: UUID, val product: Product, val quantity: Int,
    val warehouse: Warehouse
)


data class StockMovement(
    val id: UUID,
    val origin: Stock?,
    val destiny: Stock?,
    val quantity: Int,
    val type: StockMovementType,
    val date: ZonedDateTime
)


enum class StockMovementType {
    ADD,
    REMOVE,
    SELL,
    MOVE
}
