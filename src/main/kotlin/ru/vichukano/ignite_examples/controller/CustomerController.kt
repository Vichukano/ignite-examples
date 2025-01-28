package ru.vichukano.ignite_examples.controller

import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import ru.vichukano.ignite_examples.module.Customer
import ru.vichukano.ignite_examples.repository.CustomerRepository
import java.sql.Timestamp
import java.time.Instant
import kotlin.random.Random

@RestController
class CustomerController(
    private val repository: CustomerRepository
) {

    @PostMapping("/customer/{name}")
    fun createCustomer(@PathVariable name: String): Customer {
        val id = Random.nextLong()
        val customer = Customer(
            id = id,
            name = name,
            createdAt = Timestamp.from(Instant.now()),
            orderEntities = listOf()
        )
        repository.save(customer.id, customer)
        log.info { "Created Customer: $customer" }
        return customer
    }

    @GetMapping("/customer/{name}")
    fun getCustomer(@PathVariable name: String): Customer {
        return repository.findCustomerByNameOrderByCreatedAtAsc(name).also {
            log.info { "Found customer: $it" }
        } ?: throw IllegalStateException("Customer with name: $name not found")
    }

    private companion object {
        private val log = KotlinLogging.logger {}
    }

}
