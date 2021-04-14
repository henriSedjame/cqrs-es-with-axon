package io.fleet.reservationservice.data.entities.projections

import io.fleet.core.bookings.*
import io.fleet.reservationservice.core.*
import io.fleet.reservationservice.data.entities.BookingStatus
import io.fleet.reservationservice.data.entities.CarBookingView
import io.fleet.reservationservice.data.entities.repositories.CarBookingViewRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Component
import java.util.*
import java.util.logging.Logger

@Component
data class CarBookingProjections(
    val repository: CarBookingViewRepository,
    val queryUpdateEmitter: QueryUpdateEmitter
) {

    companion object {
        val logger: Logger = Logger.getLogger(CarBookingProjections::class.java.name)
    }

    /**
     * Event handling
     */

    @EventHandler
    fun on(evt: CarBookedEvent) = runBlocking {
        if (!repository.existsById(evt.bookingId)) {
            logger.info("Handle CarBookedEvent...")
            repository.insert(
                id = evt.bookingId,
                cardId = evt.carId,
                startDate = evt.startDate,
                endDate = evt.endDate,
                status = BookingStatus.CREATED
            )
            repository.findById(evt.bookingId)?.let {
                logger.info("CarBookingView created with id ${it.id}")
                queryUpdateEmitter.emit(GetAllQuery::class.java, { q -> true }, it)
            }

        }
    }

    @EventHandler
    fun on(evt: BookingUpdatedEvent) = runBlocking {
        logger.info("Handle BookingUpdatedEvent...")

        tryUpdate(evt.bookingId){
            it.copy(startDate = evt.startDate ?: it.startDate, endDate = evt.endDate ?: it.endDate)
        }

    }

    @EventHandler
    fun on(evt: BookingCancelledEvent) = runBlocking{
        logger.info("Handle BookingCancelledEvent...")

        tryUpdate(evt.bookingId){
            it.copy(status = BookingStatus.CANCELLED)
        }
    }

    @EventHandler
    fun on(evt: BookingStartedEvent) = runBlocking{
        logger.info("Handle BookingStartedEvent...")

        tryUpdate(evt.bookingId){
            it.copy(status = BookingStatus.STARTED)
        }
    }

    @EventHandler
    fun on(evt: BookingReturnedEvent) = runBlocking{
        logger.info("Handle BookingReturnedEvent...")

        tryUpdate(evt.bookingId){
            it.copy(status = BookingStatus.ENDED)
        }
    }

    /**
     * Query handling
     */

    @QueryHandler
    fun getById(query: GetByIdQuery): CarBookingView? = runBlocking{
        repository.findById(UUID.fromString(query.id))
    }

    @QueryHandler
    fun getByCarId(query: GetByCarIdQuery): List<CarBookingView> = runBlocking {
        repository.findByCarId(UUID.fromString(query.carId))
    }

    @QueryHandler
    fun getByStatus(query: GetByStatusQuery) = runBlocking {
        repository.findByStatus(query.status)
    }

    @QueryHandler
    fun getAll(query: GetAllQuery): List<CarBookingView> = runBlocking {
        repository.findAll().toList()
    }

    /**
     * private methods
     */

    private suspend fun tryUpdate(id: UUID, copy:  (CarBookingView) -> CarBookingView) {
        if (!repository.existsById(id)) {
            logger.warning("Booking with id $id doesn't exit.")
            throw NotExistException()
        }

        repository.findById(id)?.let {
            copy(it).let { copied ->
                repository.save(copied).let { updated ->
                    logger.info("CarBookingView updated")
                    queryUpdateEmitter.emit(GetAllQuery::class.java, { q -> true }, updated)
                }
            }
        }
    }

}
