package io.fleet.coremq

import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel

interface CarEventProcessor {
    companion object {
        const val GROUP = "fleet-gp"
        const val QUEUE = "CarEvents.fleet-gp"
        const val CAR_INPUT = "car-input"
        const val CAR_OUTPUT = "car-output"
    }

    @Input(CAR_INPUT)
    fun carInput(): SubscribableChannel

    @Output(CAR_OUTPUT)
    fun carOutput(): MessageChannel
}
