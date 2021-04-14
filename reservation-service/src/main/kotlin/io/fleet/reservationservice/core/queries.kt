package io.fleet.reservationservice.core

import io.fleet.reservationservice.data.entities.BookingStatus
import java.time.LocalDateTime

class GetAllQuery

data class GetByIdQuery(val id: String)

data class GetByCarIdQuery(val carId: String)

data class GetByStatusQuery(val status: BookingStatus)

data class GetBookedCarIdsAt(val dateTime: LocalDateTime)
