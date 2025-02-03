package ru.vichukano.ignite_examples.config

import mu.KotlinLogging
import org.apache.ignite.cache.CacheAtomicityMode
import org.apache.ignite.cache.QueryEntity
import org.apache.ignite.client.ClientCache
import org.apache.ignite.client.ClientCacheConfiguration
import org.apache.ignite.client.IgniteClient
import org.apache.ignite.transactions.spring.IgniteClientSpringTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import ru.vichukano.ignite_examples.model.Customer
import ru.vichukano.ignite_examples.model.OrderEntity

@Configuration
class AppConfig {

    @Bean
    fun customerCache(client: IgniteClient): ClientCache<Long, Customer> {
        val config = ClientCacheConfiguration()
            .setName("customer-cache")
            .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
            .setSqlSchema("PUBLIC")
            .setQueryEntities(
                QueryEntity(Long::class.java, Customer::class.java)
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
                QueryEntity(Long::class.java, OrderEntity::class.java)
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
