package ru.vichukano.ignite_examples.module

import org.apache.ignite.cache.query.annotations.QuerySqlField

data class OrderEntity(
    val id: Long,
    val item: String,
    val price: Int,
    @QuerySqlField(index = true)
    val customerId: Long,
)
