package io.fleet.reservationservice.core

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime
import java.util.*

data class BookCarCommand(
    @TargetAggregateIdentifier
    val bookingId : UUID,
    val carId : UUID,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime
)

data class UpdateBookingCommand(
    @TargetAggregateIdentifier
    val bookingId : UUID,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?
)

data class CancelBookingCommand(
    @TargetAggregateIdentifier
    val bookingId : UUID,
)

data class StartBookingCommand(
    @TargetAggregateIdentifier
    val bookingId : UUID,
)

data class ReturnBookingCommand(
    @TargetAggregateIdentifier
    val bookingId : UUID,
)
