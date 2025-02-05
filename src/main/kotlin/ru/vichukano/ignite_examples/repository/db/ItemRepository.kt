package ru.vichukano.ignite_examples.repository.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.vichukano.ignite_examples.model.ItemEntity

@Repository
interface ItemRepository : JpaRepository<ItemEntity, Long> {

}