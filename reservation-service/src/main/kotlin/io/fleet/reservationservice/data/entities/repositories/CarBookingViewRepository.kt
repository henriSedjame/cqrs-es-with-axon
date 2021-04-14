package io.fleet.reservationservice.data.entities.repositories

import io.fleet.reservationservice.data.entities.BookingStatus
import io.fleet.reservationservice.data.entities.CarBookingView
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime
import java.util.*

interface CarBookingViewRepository : CoroutineCrudRepository<CarBookingView, UUID> {

    @Query("INSERT INTO bookings (id, card_id, start_date, end_date, status) VALUES (:id, :cardid, :start, :end, :status)")
    suspend fun insert(
        @Param("id") id: UUID,
        @Param("cardid") cardId: UUID,
        @Param("start") startDate: LocalDateTime,
        @Param("end") endDate: LocalDateTime,
        @Param("status") status: BookingStatus
    ): CarBookingView


    suspend fun findByCarId(cardId: UUID): List<CarBookingView>

    suspend fun findByStatus(status: BookingStatus): List<CarBookingView>
}
