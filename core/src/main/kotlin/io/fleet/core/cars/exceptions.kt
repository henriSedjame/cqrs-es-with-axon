package io.fleet.core.cars


sealed class CarException(override val message: String) : Exception()

class UnRemovable: CarException("Car cannot be removed.")

class UnBookeable : CarException("Car cannot be booked.")
