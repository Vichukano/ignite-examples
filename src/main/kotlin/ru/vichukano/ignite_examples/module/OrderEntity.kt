package ru.vichukano.ignite_examples.module

data class OrderEntity(
    val id: Long,
    val item: String,
    val price: Int,
    val customerId: Long,
)
