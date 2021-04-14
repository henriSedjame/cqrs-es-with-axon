package io.fleet.securityconfig

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = [ApiSecurityRules::class, AuthServer::class])
class AutoConfiguration
