package io.fleet.core.bookings

import java.time.LocalDateTime
import java.util.*

data class CarBookedEvent(
    val bookingId : UUID,
    val carId : UUID,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime
)

data class BookingUpdatedEvent(
    val bookingId : UUID,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?
)

data class BookingCancelledEvent(val bookingId: UUID)

data class BookingStartedEvent(val bookingId: UUID)

data class BookingReturnedEvent(val bookingId: UUID)


