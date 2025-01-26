package ru.vichukano.ignite_examples.controller

import mu.KotlinLogging
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import ru.vichukano.ignite_examples.module.Customer
import ru.vichukano.ignite_examples.module.Order
import ru.vichukano.ignite_examples.repository.CustomerRepository
import kotlin.random.Random

@RestController
class OrderController(
    private val customerRepository: CustomerRepository
) {

    @PostMapping("/order/{name}/{item}/{price}")
    fun createOrder(
        @PathVariable name: String,
        @PathVariable item: String,
        @PathVariable price: Int,
    ): Customer {
        val order = Order(
            id = Random.nextLong(),
            item = item,
            price = price
        )
        val customer = customerRepository.findCustomerByName(name)!!
        val orders = customer.orders
        val updated = customer.copy(orders = orders + order)
        customerRepository.save(customer.id, customer)
        return updated.also {
            log.info { "Customer with orders: $it " }
        }
    }

    private companion object {
        private val log = KotlinLogging.logger {}
    }
}