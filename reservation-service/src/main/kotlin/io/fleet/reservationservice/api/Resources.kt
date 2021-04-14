package io.fleet.reservationservice.api

import io.fleet.reservationservice.core.GetAllQuery
import io.fleet.reservationservice.data.entities.CarBookingView
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
data class Resources(val queryGateway: QueryGateway) {

    @GetMapping("/watch", produces = [ MediaType.APPLICATION_STREAM_JSON_VALUE ])
    fun watch() : Flux<CarBookingView> {

        return queryGateway.subscriptionQuery(
            GetAllQuery(),
            ResponseTypes.multipleInstancesOf(CarBookingView::class.java),
            ResponseTypes.instanceOf(CarBookingView::class.java)
        ).let {sub ->
            sub.initialResult().flatMapMany { Flux.fromIterable(it) }.concatWith(sub.updates())
        }
    }
}
