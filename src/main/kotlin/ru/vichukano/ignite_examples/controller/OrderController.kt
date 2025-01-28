package ru.vichukano.ignite_examples.controller

import mu.KotlinLogging
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import ru.vichukano.ignite_examples.module.Customer
import ru.vichukano.ignite_examples.module.OrderEntity
import ru.vichukano.ignite_examples.repository.CustomerRepository
import ru.vichukano.ignite_examples.repository.OrderRepository
import kotlin.random.Random

@RestController
class OrderController(
    private val customerRepository: CustomerRepository,
    private val orderRepository: OrderRepository,
) {

    @PostMapping("/order/{name}/{item}/{price}")
    fun createOrder(
        @PathVariable name: String,
        @PathVariable item: String,
        @PathVariable price: Int,
    ): Customer {
        val customer = customerRepository.findCustomerByNameOrderByCreatedAtAsc(name)!!
        val orderEntity = OrderEntity(
            id = Random.nextLong(),
            item = item,
            price = price,
            customerId = customer.id,
        )
        orderRepository.save(orderEntity.id, orderEntity)
        val orders = orderRepository.findOrdersByCustomerId(customer.id)
        val customerWithOrders = customer.copy(orderEntities = orders)
        return customerWithOrders.also {
            log.info { "Customer with orders: $it " }
        }
    }

    @DeleteMapping("/order/{name}/{item}")
    fun deleteOrder(
        @PathVariable name: String,
        @PathVariable item: String
    ): Customer {
        val customer = customerRepository.findCustomerByNameOrderByCreatedAtAsc(name)!!
        val orders = orderRepository.findOrdersByCustomerId(customer.id)
        val foundByItem = orders.filter { it.item == item }
        if (foundByItem.isNotEmpty()) {
            orderRepository.deleteAllById(foundByItem.map { it.id })
        }
        val filtered = orders - foundByItem
        return customer.copy(orderEntities = filtered).also {
            log.info { "Customer with orders: $it" }
        }
    }

    private companion object {
        private val log = KotlinLogging.logger {}
    }
}