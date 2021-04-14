package io.fleet.parkingservice.data.aggregates

import io.fleet.core.bookings.CarBookedEvent
import io.fleet.core.cars.*
import io.fleet.parkingservice.core.AddCarCommand
import io.fleet.parkingservice.core.AcceptCarBookingCommand
import io.fleet.parkingservice.core.RemoveCarCommand
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.util.*
import kotlin.properties.Delegates

@Aggregate
class CarAggregate {

    @AggregateIdentifier
    lateinit var carId: UUID
    var booked by Delegates.notNull<Boolean>()
    var available by Delegates.notNull<Boolean>()


    constructor()

    /**
     * Commands handling
     */

    @CommandHandler
    constructor(cmd: AddCarCommand){
        AggregateLifecycle.apply(
            CarAddedEvent(
                carId = cmd.carId,
                immatriculation = cmd.immatriculation,
                marque = cmd.marque

            )
        )
    }

    @CommandHandler
    fun handle(cmd: AcceptCarBookingCommand){
        if(!available) throw UnBookeable()

        AggregateLifecycle.apply(
            BookingCarAcceptedEvent(
                carId = cmd.carId,
                startDate = cmd.startDate,
                endDate = cmd.endDate
            )
        )
    }

    @CommandHandler
    fun handle(cmd: RemoveCarCommand){
        if (booked) throw UnRemovable()
        AggregateLifecycle.apply(
            CarRemovedEvent(carId= cmd.carId)
        )
    }

    /**
     * Event sourcing handling
     */

    @EventSourcingHandler
    fun on(evt: CarAddedEvent) {
        carId = evt.carId
        booked = false
        available = true
    }

    @EventSourcingHandler
    fun on(evt: CarRemovedEvent){
        carId = evt.carId
        booked = false
        available = true
    }

    @EventSourcingHandler
    fun on(evt: BookingCarAcceptedEvent) {
        carId = evt.carId
        booked = true
        available =  false
    }
}
