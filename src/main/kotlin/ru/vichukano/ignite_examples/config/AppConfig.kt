package ru.vichukano.ignite_examples.config

import mu.KotlinLogging
import org.apache.ignite.cache.CacheAtomicityMode
import org.apache.ignite.cache.QueryEntity
import org.apache.ignite.cache.QueryIndex
import org.apache.ignite.client.ClientCache
import org.apache.ignite.client.ClientCacheConfiguration
import org.apache.ignite.client.IgniteClient
import org.apache.ignite.transactions.spring.IgniteClientSpringTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import ru.vichukano.ignite_examples.module.Customer
import ru.vichukano.ignite_examples.module.OrderEntity
import java.sql.Timestamp

@Configuration
class AppConfig {

    @Bean
    fun customerCache(client: IgniteClient): ClientCache<Long, Customer> {
        val config = ClientCacheConfiguration()
            .setName("customer-cache")
            .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
            .setSqlSchema("PUBLIC")
            .setQueryEntities(
                QueryEntity()
                    .setKeyType(Long::class.java.name)
                    .setValueType(Customer::class.java.name)
                    .setTableName("Customer")
                    .addQueryField("id", Long::class.java.name, null)
                    .addQueryField("name", String::class.java.name, null)
                    .addQueryField("createdAt", Timestamp::class.java.name, null)
                    .setIndexes(listOf(QueryIndex("name")))
            )
        return client.getOrCreateCache<Long?, Customer?>(config).also {
            log.info { "Created customer cache: $it" }
        }
    }

    @Bean
    fun orderCache(client: IgniteClient): ClientCache<Long, OrderEntity> {
        val config = ClientCacheConfiguration()
            .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
            .setName("order-cache")
            .setSqlSchema("PUBLIC")
            .setQueryEntities(
                QueryEntity()
                    .setKeyType(Long::class.java.name)
                    .setValueType(OrderEntity::class.java.name)
                    .setTableName(OrderEntity::class.simpleName)
                    .addQueryField("id", Long::class.java.name, null)
                    .addQueryField("item", String::class.java.name, null)
                    .addQueryField("price", Int::class.java.name, null)
                    .addQueryField("customerId", Long::class.java.name, null)
                    .setIndexes(listOf(QueryIndex("customerId", true)))
            )
        return client.getOrCreateCache<Long?, OrderEntity?>(config).also {
            log.info { "Created order cache: $it" }
        }
    }

    @Bean
    fun transactionManager(igniteClient: IgniteClient): PlatformTransactionManager {
        val txMan = IgniteClientSpringTransactionManager()
        txMan.clientInstance = igniteClient
        return txMan
    }

    private companion object {
        private val log = KotlinLogging.logger {}
    }
}
