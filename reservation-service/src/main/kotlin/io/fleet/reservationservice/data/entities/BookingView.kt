package io.fleet.reservationservice.data.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*


@Table("bookings")
data class CarBookingView(
    @Id val id: UUID,
    @Column("card_id") val carId: UUID,
    @Column("start_date") var startDate: LocalDateTime,
    @Column("end_date") var endDate: LocalDateTime,
    @Column("status") var status: BookingStatus
)

enum class BookingStatus {
    CREATED,  STARTED, ENDED, CANCELLED
}
