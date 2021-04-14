package io.fleet.reservationservice.data.aggregates

import io.fleet.core.bookings.*
import io.fleet.reservationservice.core.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.util.*
import kotlin.properties.Delegates

@Aggregate
class BookingAggregate {
    @AggregateIdentifier
    lateinit var bookingId: UUID
    var started by Delegates.notNull<Boolean>()
    var ended by Delegates.notNull<Boolean>()

    constructor()

    /**
     * Commands handling
     */

    @CommandHandler
    constructor(cmd: BookCarCommand) : this() {
        AggregateLifecycle.apply(
            CarBookedEvent(
            bookingId = cmd.bookingId,
            carId = cmd.carId,
            startDate = cmd.startDate,
            endDate = cmd.endDate
        ))
    }

    @CommandHandler
    fun handle(cmd: UpdateBookingCommand) {
        applyIf(
            condition = !started && !ended,
            exception = UnUpdateableException(),
            evt = BookingUpdatedEvent(
                bookingId = cmd.bookingId,
                startDate = cmd.startDate,
                endDate = cmd.endDate
            ))
    }

    @CommandHandler
    fun handle(cmd: CancelBookingCommand) {
        applyIf(
            condition = !started && !ended,
            exception = UnCancellable(),
            evt = BookingCancelledEvent(
                bookingId = cmd.bookingId
            )
        )
    }

    @CommandHandler
    fun handle(cmd: StartBookingCommand) {
        applyIf(
            condition = !started && !ended,
            exception = UnStartable(),
            evt = BookingStartedEvent(
                bookingId = cmd.bookingId
            )
        )
    }

    @CommandHandler
    fun handle(cmd: ReturnBookingCommand) {
        applyIf(
            condition = started && !ended,
            exception = UnReturnable(),
            evt = BookingReturnedEvent(
                bookingId = cmd.bookingId
            )
        )
    }

    /**
     *  Event sourcing handling
     */

    @EventSourcingHandler
    fun on(evt: CarBookedEvent) {
        bookingId = evt.bookingId
        started = false
        ended = false
    }

    @EventSourcingHandler
    fun on(evt: BookingUpdatedEvent){
        bookingId = evt.bookingId
    }

    @EventSourcingHandler
    fun on(evt: BookingCancelledEvent){
        bookingId = evt.bookingId
        ended = true
    }

    @EventSourcingHandler
    fun on(evt: BookingStartedEvent){
        bookingId = evt.bookingId
        started = true
    }

    @EventSourcingHandler
    fun on(evt: BookingReturnedEvent){
        bookingId = evt.bookingId
        ended = true
    }

    /**
     * Private methods
     */

    private fun  <T> applyIf(condition: Boolean, exception: BookingException, evt: T) {
        if(condition) AggregateLifecycle.apply(evt)
        else throw exception
    }
}
