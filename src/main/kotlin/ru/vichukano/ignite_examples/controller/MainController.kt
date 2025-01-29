package ru.vichukano.ignite_examples.controller

import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import ru.vichukano.ignite_examples.module.Customer
import ru.vichukano.ignite_examples.service.CustomerOrderService

@RestController
class MainController(
    private val service: CustomerOrderService
) {

    @PostMapping("/order/{name}/{item}/{price}")
    fun createOrder(
        @PathVariable name: String,
        @PathVariable item: String,
        @PathVariable price: Int,
    ): Customer = service.createOrderForCustomer(
        customerName = name,
        itemName = item,
        price = price
    ).also {
        log.info { "Customer with orders: $it " }
    }

    @DeleteMapping("/order/{name}/{item}")
    fun deleteOrder(
        @PathVariable name: String,
        @PathVariable item: String
    ): Customer = service.deleteOrderFromCustomer(
        customerName = name,
        itemName = item
    ).also {
        log.info { "Customer with orders: $it" }
    }

    @PostMapping("/customer/{name}")
    fun createCustomer(@PathVariable name: String): Customer = service.createCustomer(name)

    @GetMapping("/customer/{name}")
    fun getCustomer(@PathVariable name: String): Customer = service.getCustomer(name)

    private companion object {
        private val log = KotlinLogging.logger { }
    }
}
