package io.fleet.securityconfig

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("api.security.rules")
class ApiSecurityRules {
    var permitAll: MutableSet<String>? = null
    var permitByRole: MutableMap<String, MutableSet<String>>? = null
    var userRetrieveUri : String? = null
}
