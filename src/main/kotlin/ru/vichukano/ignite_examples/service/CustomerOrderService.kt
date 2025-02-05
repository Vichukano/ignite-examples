package ru.vichukano.ignite_examples.service

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.vichukano.ignite_examples.model.Customer
import ru.vichukano.ignite_examples.model.OrderEntity
import ru.vichukano.ignite_examples.repository.ignite.CustomerRepository
import ru.vichukano.ignite_examples.repository.ignite.OrderRepository
import java.sql.Timestamp
import java.time.Instant
import kotlin.random.Random

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
        return customer.copy(orderEntities = filtered)
    }

    fun createCustomer(name: String): Customer {
        val id = Random.nextLong()
        val customer = Customer(
            id = id,
            name = name,
            createdAt = Timestamp.from(Instant.now()),
            orderEntities = listOf(),
            updatedAt = Instant.now(),
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
