package ru.vichukano.ignite_examples.repository

import org.apache.ignite.springdata.repository.IgniteRepository
import org.apache.ignite.springdata.repository.config.RepositoryConfig
import ru.vichukano.ignite_examples.model.OrderEntity

@RepositoryConfig(cacheName = "order-cache")
interface OrderRepository : IgniteRepository<OrderEntity, Long> {

    fun findOrdersByCustomerId(customerId: Long): List<OrderEntity>

}