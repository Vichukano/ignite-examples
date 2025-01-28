package ru.vichukano.ignite_examples.config

import org.apache.ignite.cache.QueryEntity
import org.apache.ignite.cache.QueryIndex
import org.apache.ignite.client.ClientCache
import org.apache.ignite.client.ClientCacheConfiguration
import org.apache.ignite.client.IgniteClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.vichukano.ignite_examples.module.Customer

@Configuration
class AppConfig {

    @Bean
    fun cache(client: IgniteClient): ClientCache<Long, Customer> {
        val config = ClientCacheConfiguration()
            .setName("customer-cache")
            .setSqlSchema("PUBLIC")
            .setQueryEntities(
                QueryEntity()
                    .setKeyType(Long::class.java.name)
                    .setValueType(Customer::class.java.name)
                    .setTableName("Customer")
                    .addQueryField("id", Long::class.java.name, null)
                    .addQueryField("name", String::class.java.name, null)
                    .setIndexes(
                        listOf(
                            QueryIndex("name")
                        )
                    )
            )
        return client.getOrCreateCache(config)
    }

}