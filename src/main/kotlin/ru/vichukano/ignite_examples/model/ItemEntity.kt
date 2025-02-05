package ru.vichukano.ignite_examples.model

import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "items")
class ItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val createdAt: Instant
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ItemEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return ItemEntity::class.hashCode()
    }

    override fun toString(): String {
        return "ItemEntity(id=$id, createdAt=$createdAt)"
    }

}