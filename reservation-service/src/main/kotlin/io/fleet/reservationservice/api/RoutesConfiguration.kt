package io.fleet.reservationservice.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RoutesConfiguration {

    @ApiDocumentation
    @Bean
    fun routes(handler: RoutesHandler) = coRouter {

        BASE_ROUTE.nest {

            accept(MediaType.APPLICATION_JSON).nest {

                COMMANDS_BASE_ROUTE.nest {
                    POST("", handler::book)
                    PUT(UPDATE_COMMAND, handler::update)
                    PUT(CANCEL_COMMAND, handler::cancel)
                    PUT(START_COMMAND, handler::start)
                    PUT(RETURN_COMMAND, handler::end)
                }

                QUERY_BASE_ROUTE.nest {
                    GET("", handler::all)
                    GET(BY_ID_QUERY, handler::byId)
                    GET(BY_CAR_ID_QUERY, handler::byCarId)
                    GET(BY_STATUS, handler::byStatus)
                }
            }
        }
    }
}
