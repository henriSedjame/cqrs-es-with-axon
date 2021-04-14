package io.fleet.securityconfig

import org.springframework.http.HttpMethod
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

object SecurityUtils {

    fun corsConfiguration() : CorsConfigurationSource {
        return UrlBasedCorsConfigurationSource().apply {
            this.registerCorsConfiguration("/**", CorsConfiguration().apply {
                allowCredentials = true
                addAllowedHeader("*")
                addAllowedOrigin("*")
                addAllowedMethod(HttpMethod.GET)
                addAllowedMethod(HttpMethod.POST)
                addAllowedMethod(HttpMethod.PUT)
                addAllowedMethod(HttpMethod.DELETE)
                addAllowedMethod(HttpMethod.OPTIONS)
                addAllowedMethod(HttpMethod.HEAD)
                addAllowedMethod(HttpMethod.PATCH)
            })
        }
    }


}
