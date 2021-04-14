package io.fleet.reservationservice.handlers

import io.fleet.core.cars.BookingCarAcceptedEvent
import io.fleet.reservationservice.core.BookCarCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.extensions.kotlin.send
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component
@ProcessingGroup("carEvents")
class ExternalEventHandlers(val commandGateway: CommandGateway) {

    @EventHandler
    fun on(evt: BookingCarAcceptedEvent){

        commandGateway.send<BookCarCommand>(
            BookCarCommand(
                bookingId = UUID.randomUUID(),
                carId = evt.carId,
                startDate = evt.startDate,
                endDate = evt.endDate
            )
        )

    }

}
