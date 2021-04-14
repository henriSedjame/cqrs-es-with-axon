package io.fleet.adminservice.api

import io.fleet.adminservice.data.Client
import io.fleet.adminservice.data.ClientCredentials
import io.fleet.adminservice.data.User
import io.fleet.adminservice.data.UserCredentials
import io.fleet.adminservice.service.KeycloakService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AdminController(val service: KeycloakService) {

    companion object {
        const val REALM = "realm"
        const val USERNAME = "Username"
        const val PASSWORD = "Password"
        const val CLIENT_ID = "Client-Id"
        const val CLIENT_SECRET = "Client-Secret"
    }

    @PostMapping("{realm}/user")
    fun addUser(
        @PathVariable(REALM) realm: String,
        @RequestHeader(USERNAME) username: String,
        @RequestHeader(PASSWORD) password: String,
        @RequestHeader(CLIENT_ID) clientId: String,
        @RequestHeader(CLIENT_SECRET) clientSecret: String,
        @RequestBody user: User
    ): ResponseEntity<Any> {
        return service.addUser(
            realm,
            ClientCredentials(clientId, clientSecret),
            UserCredentials(username, password),
            user
        )
            .let { ResponseEntity.status(it.status).body(it.entity) }
    }

    @PostMapping("{realm}/client")
    fun addClient(
        @PathVariable(REALM) realm: String,
        @RequestHeader(USERNAME) username: String,
        @RequestHeader(PASSWORD) password: String,
        @RequestBody client: Client
    ): ResponseEntity<Any> {
        return service.addClient(
            realm,
            UserCredentials(username, password),
            client
        )
            .let { ResponseEntity.status(it.status).body(it.entity) }
    }

}
