package io.fleet.reservationservice.api

import io.fleet.core.cars.TryBookCarEvent
import io.fleet.reservationservice.api.dto.BookCarRequest
import io.fleet.reservationservice.api.dto.UpdateBookingRequest
import io.fleet.reservationservice.core.*
import io.fleet.reservationservice.data.entities.BookingStatus
import io.fleet.reservationservice.data.entities.CarBookingView
import kotlinx.coroutines.runBlocking
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import java.time.LocalDateTime
import java.util.*

@Component
data class RoutesHandler(val commandGateway: CommandGateway, val queryGateway: QueryGateway){

    companion object{
        const val BOOK_CAR = "book"
        const val UPDATE_BOOKING = "update"
        const val CANCEL_BOOKING = "cancel"
        const val START_BOOKING = "start"
        const val RETURN_BOOKING = "end"
        const val GET_BY_ID = "byId"
        const val GET_CAR_BY_ID = "byCarId"
        const val GET_BY_STATUS = "byStatus"
        const val GET_ALL = "all"
    }

    suspend fun book(request: ServerRequest) : ServerResponse {
        return request.awaitBody(BookCarRequest::class).let {
            AggregateLifecycle.apply(TryBookCarEvent(
                carId = UUID.fromString(it.carId),
                startDate = LocalDateTime.parse(it.startDate),
                endDate = LocalDateTime.parse(it.endDate)
            ))
            /*commandGateway.send<BookCarCommand>(
                BookCarCommand(
                bookingId = UUID.randomUUID(),
                carId = UUID.fromString(it.carId),
                startDate = LocalDateTime.parse(it.startDate),
                endDate = LocalDateTime.parse(it.endDate)
            ))*/
            ServerResponse.ok().buildAndAwait()
        }
    }

    suspend fun update(request: ServerRequest): ServerResponse {
        val id = request.pathVariable(PathVars.id)

        return request.awaitBody(UpdateBookingRequest::class).let{
            commandGateway.send<UpdateBookingCommand>(
                UpdateBookingCommand(
                    bookingId = UUID.fromString(id),
                    startDate = it.startDate?.let { d -> LocalDateTime.parse(d) },
                    endDate = it.endDate?.let { d -> LocalDateTime.parse(d) },
                )
            )
            ServerResponse.ok().buildAndAwait()
        }
    }

    suspend fun cancel(request: ServerRequest) : ServerResponse {
        val id = request.pathVariable(PathVars.id)
        commandGateway.send<CancelBookingCommand>(
            CancelBookingCommand(bookingId = UUID.fromString(id))
        )
        return ServerResponse.ok().buildAndAwait()
    }

    suspend fun start(request: ServerRequest) : ServerResponse {
        val id = request.pathVariable(PathVars.id)
        commandGateway.send<StartBookingCommand>(
            StartBookingCommand(bookingId = UUID.fromString(id))
        )
        return ServerResponse.ok().buildAndAwait()
    }

    suspend fun end(request: ServerRequest) : ServerResponse {
        val id = request.pathVariable(PathVars.id)
        commandGateway.send<ReturnBookingCommand>(
            ReturnBookingCommand(bookingId = UUID.fromString(id))
        )
        return ServerResponse.ok().buildAndAwait()
    }


    suspend fun byId(request: ServerRequest) : ServerResponse {
        return request.pathVariable(PathVars.id)
            .let {
                queryGateway.query(GetByIdQuery(id = it), ResponseTypes.instanceOf(CarBookingView::class.java))
                    .get()
                    .let { view ->
                        ServerResponse.ok().bodyValueAndAwait(view)
                    }
            }
    }

    suspend fun byCarId(request: ServerRequest) : ServerResponse {
        return request.pathVariable(PathVars.cardId)
            .let {
                queryGateway.query(GetByCarIdQuery(carId = it), ResponseTypes.multipleInstancesOf(CarBookingView::class.java))
                    .get()
                    .let { view ->
                        ServerResponse.ok().bodyValueAndAwait(view)
                    }
            }
    }

    suspend fun byStatus(request: ServerRequest) : ServerResponse {
        return request.pathVariable(PathVars.status)
            .let {
                 BookingStatus.valueOf(it).let { status ->
                    queryGateway.query(GetByStatusQuery(status), ResponseTypes.multipleInstancesOf(CarBookingView::class.java))
                        .get()
                        .let{ views ->
                            ServerResponse.ok().bodyValueAndAwait(views)
                        }
                }
            }
    }

    suspend fun all(request: ServerRequest) : ServerResponse {
        return queryGateway.query(GetAllQuery(), ResponseTypes.multipleInstancesOf(CarBookingView::class.java))
            .get()
            .let {
               ServerResponse.ok().bodyValueAndAwait(it)
            }
    }
}

