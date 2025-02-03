package ru.vichukano.ignite_examples.model

import org.apache.ignite.cache.query.annotations.QuerySqlField
import java.sql.Timestamp
import java.time.Instant

data class Customer(
    @QuerySqlField
    val id: Long,
    @QuerySqlField(index = true)
    val name: String,
    @QuerySqlField
    val createdAt: Timestamp,
    val updatedAt: Instant,
    val randomInt: Int,
    val strings: List<String>,
    val somethings: Set<Something>,
    val orderEntities: List<OrderEntity>,
)
