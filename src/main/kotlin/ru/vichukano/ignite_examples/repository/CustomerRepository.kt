package ru.vichukano.ignite_examples.repository

import org.apache.ignite.springdata.repository.IgniteRepository
import org.apache.ignite.springdata.repository.config.RepositoryConfig
import ru.vichukano.ignite_examples.module.Customer

@RepositoryConfig(cacheName = "person-cache3")
interface CustomerRepository : IgniteRepository<Customer, Long> {

    fun findCustomerByName(name: String): Customer?

}