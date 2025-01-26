package ru.vichukano.ignite_examples

import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableIgniteRepositories
class IgniteExamplesApplication

fun main(args: Array<String>) {
	runApplication<IgniteExamplesApplication>(*args)
}
