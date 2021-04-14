package io.fleet.securityconfig

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SecurityConfigApplication

fun main(args: Array<String>) {
    runApplication<SecurityConfigApplication>(*args)
}
