package io.fleet.coremq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoreMqApplication

fun main(args: Array<String>) {
    runApplication<CoreMqApplication>(*args)
}
