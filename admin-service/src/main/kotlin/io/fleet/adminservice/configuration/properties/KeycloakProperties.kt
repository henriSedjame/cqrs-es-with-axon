package io.fleet.adminservice.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("keycloak")
class KeycloakProperties {
    lateinit var serverUrl: String
}
