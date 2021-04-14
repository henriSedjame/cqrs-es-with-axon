package io.fleet.parkingservice.handlers

import io.fleet.core.cars.TryBookCarEvent
import io.fleet.parkingservice.core.AcceptCarBookingCommand
import io.fleet.parkingservice.data.entities.repositories.CarViewRepository
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import java.util.logging.Logger

@ProcessingGroup("bookingEvents")
@Component
class ExternalEventHandlers(val carViewRepository: CarViewRepository, val commandGateway: CommandGateway) {

    companion object {
        val logger: Logger = Logger.getLogger(ExternalEventHandlers::class.simpleName)
    }

    @EventHandler
    fun on(evt: TryBookCarEvent){
        // check if booking is possible

        // if ok
        commandGateway.send<AcceptCarBookingCommand>(AcceptCarBookingCommand(
            carId = evt.carId,
            startDate = evt.startDate,
            endDate = evt.endDate
        )).thenAccept{ logger.info("") }
    }
}
