package ru.vichukano.ignite_examples.model

import org.apache.ignite.cache.query.annotations.QuerySqlField

data class OrderEntity(
    @QuerySqlField
    val id: Long,
    @QuerySqlField
    val item: String,
    @QuerySqlField
    val price: Int,
    @QuerySqlField(index = true)
    val customerId: Long,
)
