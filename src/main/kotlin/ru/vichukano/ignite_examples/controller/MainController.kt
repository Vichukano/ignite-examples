package ru.vichukano.ignite_examples.controller

import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import ru.vichukano.ignite_examples.model.Customer
import ru.vichukano.ignite_examples.model.ItemEntity
import ru.vichukano.ignite_examples.repository.db.ItemRepository
import ru.vichukano.ignite_examples.service.CustomerOrderService
import java.time.Instant

@RestController
class MainController(
    private val service: CustomerOrderService,
    private val itemRepository: ItemRepository,
) {

    @PostMapping("/item")
    fun createItem(): ItemEntity {
        val item = ItemEntity(createdAt = Instant.now())
        itemRepository.save(item)
        return item
    }

    @GetMapping("/item/{id}")
    fun findItem(
        @PathVariable id: Long
    ): ItemEntity = itemRepository.findByIdOrNull(id)!!

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
