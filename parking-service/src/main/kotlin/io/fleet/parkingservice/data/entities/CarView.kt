package io.fleet.parkingservice.data.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("cars")
data class CarView(
    @Id val id: UUID,
    val immatriculation: String,
    val marque: String,
    val status: CarStatus,
)

enum class CarStatus {
    FREE, IN_USE, BROKEN
}
