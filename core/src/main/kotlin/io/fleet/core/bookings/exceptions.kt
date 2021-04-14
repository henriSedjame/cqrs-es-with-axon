package io.fleet.core.bookings

sealed class BookingException(override val message: String) : Exception()

class UnUpdateableException : BookingException("Booking cannot be updated.")

class UnCancellable : BookingException("Booking cannot be cancelled")

class UnStartable : BookingException("Booking cannot be started")

class UnReturnable : BookingException("Booking cannot be returned")

class NotExistException: BookingException("Booking not found.")
