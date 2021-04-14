package io.fleet.reservationservice.api

const val BASE_ROUTE = "/bookings"

const val COMMANDS_BASE_ROUTE = "/c"

const val QUERY_BASE_ROUTE = "/q"

const val UPDATE_COMMAND = "/{${PathVars.id}}"

const val CANCEL_COMMAND = "/cancel/{${PathVars.id}}"

const val START_COMMAND = "/start/{${PathVars.id}}"

const val RETURN_COMMAND = "/end/{${PathVars.id}}"

const val BY_ID_QUERY = "/{${PathVars.id}}"

const val BY_CAR_ID_QUERY = "/car/{${PathVars.cardId}}"

const val BY_STATUS = "/status/{${PathVars.status}}"


object PathVars {
    const val id = "id"
    const val cardId = "carId"
    const val status = "status"
}

object OperationIds {
    const val BOOK_CAR = "book_car"
    const val UPDATE_BOOKING = "update_booking"
    const val CANCEL_BOOKING = "cancel_booking"
    const val START_BOOKING = "start_booking"
    const val RETURN_BOOKING = "return_booking"
    const val GET_ALL = "all"
    const val GET_BY_ID = "get_by_id"
    const val GET_BY_CAR_ID = "get_by_car_id"
    const val GET_BY_STATUS = "get_by_status"
}

object OperationDescriptions {
    const val BOOK_CAR = "Create a car booking"
    const val UPDATE_BOOKING = "Update a booking"
    const val CANCEL_BOOKING = "Cancel a booking"
    const val START_BOOKING = "Start a booking"
    const val RETURN_BOOKING = "End a booking"
    const val GET_ALL = "Get all bookings"
    const val GET_BY_ID = "Get booking by id"
    const val GET_BY_CAR_ID = "Get bookings by car id"
    const val GET_BY_STATUS = "Get bookings by status"
}

object ParameterDescriptions {
    const val ID = "Booking id"
    const val CAR_ID = "Car id"
    const val STATUS = "Booking status"
}

object HttpMethods {
    const val GET = "GET"
    const val POST = "POST"
    const val PUT = "PUT"
}

object StatusCodes {
     const val SUCCESS =  "200"
}
