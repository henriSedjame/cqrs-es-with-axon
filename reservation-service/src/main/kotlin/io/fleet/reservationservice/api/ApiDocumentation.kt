package io.fleet.reservationservice.api

import io.fleet.reservationservice.api.dto.BookCarRequest
import io.fleet.reservationservice.api.dto.UpdateBookingRequest
import io.fleet.reservationservice.data.entities.CarBookingView
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.web.bind.annotation.RequestMethod

@RouterOperations(
    value = [
        RouterOperation(
            path = "$BASE_ROUTE$COMMANDS_BASE_ROUTE",
            beanClass = RoutesHandler::class,
            beanMethod = RoutesHandler.BOOK_CAR,
            method = [RequestMethod.POST],
            operation = Operation(
                operationId = OperationIds.BOOK_CAR,
                method = HttpMethods.POST,
                description = OperationDescriptions.BOOK_CAR,
                requestBody = RequestBody(content = [Content(schema = Schema(implementation = BookCarRequest::class))])
            )
        ),
        RouterOperation(
            path = "$BASE_ROUTE$COMMANDS_BASE_ROUTE$UPDATE_COMMAND",
            beanClass = RoutesHandler::class,
            beanMethod = RoutesHandler.UPDATE_BOOKING,
            method = [RequestMethod.PUT],
            operation = Operation(
                operationId = OperationIds.UPDATE_BOOKING,
                method = HttpMethods.PUT,
                description = OperationDescriptions.UPDATE_BOOKING,
                parameters = [Parameter(name = PathVars.id, `in` = ParameterIn.PATH, description = ParameterDescriptions.ID, required = true)],
                requestBody = RequestBody(content = [Content(schema = Schema(implementation = UpdateBookingRequest::class))])
            )
        ),
        RouterOperation(
            path = "$BASE_ROUTE$COMMANDS_BASE_ROUTE$CANCEL_COMMAND",
            beanClass = RoutesHandler::class,
            beanMethod = RoutesHandler.CANCEL_BOOKING,
            method = [RequestMethod.PUT],
            operation = Operation(
                operationId = OperationIds.CANCEL_BOOKING,
                method = HttpMethods.PUT,
                description = OperationDescriptions.CANCEL_BOOKING,
                parameters = [Parameter(name = PathVars.id, `in` = ParameterIn.PATH, description = ParameterDescriptions.ID, required = true)],
            )
        ),
        RouterOperation(
            path = "$BASE_ROUTE$COMMANDS_BASE_ROUTE$START_COMMAND",
            beanClass = RoutesHandler::class,
            beanMethod = RoutesHandler.START_BOOKING,
            method = [RequestMethod.PUT],
            operation = Operation(
                operationId = OperationIds.START_BOOKING,
                method = HttpMethods.PUT,
                description = OperationDescriptions.START_BOOKING,
                parameters = [Parameter(name = PathVars.id, `in` = ParameterIn.PATH, description = ParameterDescriptions.ID, required = true)],
            )
        ),
        RouterOperation(
            path = "$BASE_ROUTE$COMMANDS_BASE_ROUTE$RETURN_COMMAND",
            beanClass = RoutesHandler::class,
            beanMethod = RoutesHandler.RETURN_BOOKING,
            method = [RequestMethod.PUT],
            operation = Operation(
                operationId = OperationIds.RETURN_BOOKING,
                method = HttpMethods.PUT,
                description = OperationDescriptions.RETURN_BOOKING,
                parameters = [Parameter(name = PathVars.id, `in` = ParameterIn.PATH, description = ParameterDescriptions.ID, required = true)],
            )
        ),
        RouterOperation(
            path = "$BASE_ROUTE$QUERY_BASE_ROUTE",
            beanClass = RoutesHandler::class,
            beanMethod = RoutesHandler.GET_ALL,
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = OperationIds.GET_ALL,
                method = HttpMethods.GET,
                description = OperationDescriptions.GET_ALL,
                responses = [
                    ApiResponse(responseCode = StatusCodes.SUCCESS, content = [Content(schema = Schema(implementation = CarBookingView::class))]),
                ]
            )
        ),
        RouterOperation(
            path = "$BASE_ROUTE$QUERY_BASE_ROUTE$BY_ID_QUERY",
            beanClass = RoutesHandler::class,
            beanMethod = RoutesHandler.GET_BY_ID,
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = OperationIds.GET_BY_ID,
                method = HttpMethods.GET,
                description = OperationDescriptions.GET_BY_ID,
                parameters = [Parameter(name = PathVars.id, `in` = ParameterIn.PATH, description = ParameterDescriptions.ID, required = true)],
                responses = [
                    ApiResponse(responseCode = StatusCodes.SUCCESS, content = [Content(schema = Schema(implementation = CarBookingView::class))]),
                ]
            )
        ),
        RouterOperation(
            path = "$BASE_ROUTE$QUERY_BASE_ROUTE$BY_CAR_ID_QUERY",
            beanClass = RoutesHandler::class,
            beanMethod = RoutesHandler.GET_CAR_BY_ID,
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = OperationIds.GET_BY_CAR_ID,
                method = HttpMethods.GET,
                description = OperationDescriptions.GET_BY_CAR_ID,
                parameters = [Parameter(name = PathVars.cardId, `in` = ParameterIn.PATH, description = ParameterDescriptions.CAR_ID, required = true)],
                responses = [
                    ApiResponse(responseCode = StatusCodes.SUCCESS, content = [Content(schema = Schema(implementation = CarBookingView::class))]),
                ]
            )
        ),
        RouterOperation(
            path = "$BASE_ROUTE$QUERY_BASE_ROUTE$BY_STATUS",
            beanClass = RoutesHandler::class,
            beanMethod = RoutesHandler.GET_BY_STATUS,
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = OperationIds.GET_BY_STATUS,
                method = HttpMethods.GET,
                description = OperationDescriptions.GET_BY_STATUS,
                parameters = [Parameter(name = PathVars.status, `in` = ParameterIn.PATH, description = ParameterDescriptions.STATUS, required = true)],
                responses = [
                    ApiResponse(responseCode = StatusCodes.SUCCESS, content = [Content(schema = Schema(implementation = CarBookingView::class))]),
                ]
            )
        ),

    ]
)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiDocumentation
