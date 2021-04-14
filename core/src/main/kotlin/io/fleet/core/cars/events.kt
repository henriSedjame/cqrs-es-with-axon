package io.fleet.core.cars

import java.time.LocalDateTime
import java.util.*

data class CarAddedEvent(val carId: UUID, val immatriculation: String, val marque: String)

data class TryBookCarEvent(val carId: UUID, val startDate: LocalDateTime, val endDate: LocalDateTime)

data class BookingCarAcceptedEvent(val carId: UUID, val startDate: LocalDateTime, val endDate: LocalDateTime)

data class CarRemovedEvent(val carId: UUID)
