package ru.vichukano.ignite_examples.config

import org.apache.ignite.client.ClientCache
import org.apache.ignite.client.IgniteClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.vichukano.ignite_examples.module.Customer

@Configuration
class AppConfig {

    @Bean
    fun cache(client: IgniteClient): ClientCache<Long, Customer> {
        return client.getOrCreateCache("person-cache3")
    }

}