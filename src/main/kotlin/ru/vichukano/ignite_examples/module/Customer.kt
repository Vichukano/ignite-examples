package ru.vichukano.ignite_examples.module

import org.apache.ignite.cache.query.annotations.QuerySqlField
import java.sql.Timestamp

data class Customer(
    @QuerySqlField
    val id: Long,
    @QuerySqlField(index = true)
    val name: String,
    @QuerySqlField
    val createdAt: Timestamp,
    val orderEntities: List<OrderEntity>,
)
