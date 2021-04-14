package io.fleet.parkingservice

import io.fleet.coremq.BookingEventProcessor
import io.fleet.coremq.CarEventProcessor
import io.fleet.securityconfig.SecuredResourceServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@SpringBootApplication
@SecuredResourceServer
@EnableBinding(CarEventProcessor::class, BookingEventProcessor::class)
class ParkingServiceApplication

fun main(args: Array<String>) {
    runApplication<ParkingServiceApplication>(*args)
}

