package io.fleet.parkingservice.core

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime
import java.util.*

data class AddCarCommand(
    @TargetAggregateIdentifier val carId: UUID,
    val immatriculation: String,
    val marque: String
)

data class AcceptCarBookingCommand(
    @TargetAggregateIdentifier val carId: UUID,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime
)

data class RemoveCarCommand(@TargetAggregateIdentifier val carId: UUID)

