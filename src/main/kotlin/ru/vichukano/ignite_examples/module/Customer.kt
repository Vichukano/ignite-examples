package ru.vichukano.ignite_examples.module

import java.sql.Timestamp

data class Customer(
    val id: Long,
    val name: String,
    val createdAt: Timestamp,
    val orderEntities: List<OrderEntity>,
)
