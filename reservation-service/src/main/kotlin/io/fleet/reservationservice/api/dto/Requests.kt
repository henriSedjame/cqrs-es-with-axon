package io.fleet.reservationservice.api.dto

data class BookCarRequest(var carId: String, val startDate: String, val endDate: String)

data class UpdateBookingRequest(val startDate: String?, val endDate: String?)
