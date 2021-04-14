package io.fleet.securityconfig

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("auth.server")
class AuthServer {
    lateinit var url: String
}
