package io.fleet.coremq

import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel

interface BookingEventProcessor {
    companion object {
        const val GROUP = "fleet-gp"
        const val QUEUE = "BookingEvents.fleet-gp"
        const val BOOKING_INPUT = "booking-input"
        const val BOOKING_OUTPUT = "booking-output"
    }

    @Input(BOOKING_INPUT)
    fun bookingInput(): SubscribableChannel

    @Output(BOOKING_OUTPUT)
    fun bookingOutput(): MessageChannel
}
