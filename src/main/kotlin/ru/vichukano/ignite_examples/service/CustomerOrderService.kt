package ru.vichukano.ignite_examples.service

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.vichukano.ignite_examples.model.Customer
import ru.vichukano.ignite_examples.model.OrderEntity
import ru.vichukano.ignite_examples.model.Something
import ru.vichukano.ignite_examples.repository.CustomerRepository
import ru.vichukano.ignite_examples.repository.OrderRepository
import java.sql.Timestamp
import java.time.Instant
import kotlin.random.Random
import kotlin.random.nextInt

@Component
class CustomerOrderService(
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
) {

    @Transactional
    fun createOrderForCustomer(
        customerName: String,
        itemName: String,
        price: Int
    ): Customer {
        val customer = customerRepository.findCustomerByNameOrderByCreatedAtAsc(customerName)!!
        val orderEntity = OrderEntity(
            id = Random.nextLong(),
            item = itemName,
            price = price,
            customerId = customer.id,
        )
        orderRepository.save(orderEntity.id, orderEntity)
        val orders = orderRepository.findOrdersByCustomerId(customer.id)
        return customer.copy(orderEntities = orders + orderEntity)
    }

    @Transactional
    fun deleteOrderFromCustomer(
        customerName: String,
        itemName: String
    ): Customer {
        val customer = customerRepository.findCustomerByNameOrderByCreatedAtAsc(customerName)!!
        val orders = orderRepository.findOrdersByCustomerId(customer.id)
        val foundByItem = orders.filter { it.item == itemName }
        if (foundByItem.isNotEmpty()) {
            orderRepository.deleteAllById(foundByItem.map { it.id })
        }
        val filtered = orders - foundByItem
        //Should not delete cause transaction
        //throw RuntimeException("BOOM")
        return customer.copy(orderEntities = filtered)
    }

    fun createCustomer(name: String): Customer {
        val id = Random.nextLong()
        val customer = Customer(
            id = id,
            name = name,
            createdAt = Timestamp.from(Instant.now()),
            orderEntities = listOf(),
            randomInt = Random.nextInt(0..100),
            updatedAt = Instant.now(),
            strings = listOf("A", "B", "C"),
            somethings = setOf(Something("S", 1L), Something("X", 2L))
        )
        customerRepository.save(customer.id, customer)
        return customer
    }

    fun getCustomer(name: String): Customer {
        val customer = customerRepository.findCustomerByNameOrderByCreatedAtAsc(name)
            ?: throw IllegalStateException("Customer with name: $name not found")
        val orders = orderRepository.findOrdersByCustomerId(customer.id)
        return customer.copy(orderEntities = orders)
    }

}
