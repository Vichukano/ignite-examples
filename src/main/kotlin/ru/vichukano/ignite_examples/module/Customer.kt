package ru.vichukano.ignite_examples.module

import org.apache.ignite.cache.affinity.AffinityKeyMapped
import org.apache.ignite.cache.query.annotations.QuerySqlField
import java.time.Instant

data class Customer(
    @AffinityKeyMapped
    val id: Long,
    @QuerySqlField(index = true)
    val name: String,
    val createdAt: Instant,
    val orders: List<Order>,
)
