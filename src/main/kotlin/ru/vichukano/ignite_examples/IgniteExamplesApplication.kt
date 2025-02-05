package ru.vichukano.ignite_examples

import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableIgniteRepositories(basePackages = ["ru.vichukano.ignite_examples.repository.ignite"])
@EnableJpaRepositories(basePackages = ["ru.vichukano.ignite_examples.repository.db"])
class IgniteExamplesApplication

fun main(args: Array<String>) {
    runApplication<IgniteExamplesApplication>(*args)
}
