package io.fleet.securityconfig

import kotlinx.coroutines.reactor.mono
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
class ApiErrorHandler : ServerAccessDeniedHandler {

    override fun handle(exchange: ServerWebExchange?, denied: AccessDeniedException?): Mono<Void> = mono {
        denied?.let { error(it) }
    }

}

