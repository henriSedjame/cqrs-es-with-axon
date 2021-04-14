package io.fleet.reservationservice

import io.fleet.coremq.BookingEventProcessor
import io.fleet.coremq.CarEventProcessor
import io.fleet.securityconfig.SecuredResourceServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@SpringBootApplication
@SecuredResourceServer
@EnableBinding(BookingEventProcessor::class, CarEventProcessor::class)
class ReservationServiceApplication

fun main(args: Array<String>) {
    runApplication<ReservationServiceApplication>(*args)
}


