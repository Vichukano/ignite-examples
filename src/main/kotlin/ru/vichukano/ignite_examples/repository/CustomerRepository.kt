package ru.vichukano.ignite_examples.repository

import org.apache.ignite.springdata.repository.IgniteRepository
import org.apache.ignite.springdata.repository.config.RepositoryConfig
import ru.vichukano.ignite_examples.model.Customer

@RepositoryConfig(cacheName = "customer-cache")
interface CustomerRepository : IgniteRepository<Customer, Long> {

    fun findCustomerByNameOrderByCreatedAtAsc(name: String): Customer?

}