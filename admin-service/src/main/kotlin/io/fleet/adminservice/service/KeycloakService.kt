package io.fleet.adminservice.service

import io.fleet.adminservice.data.Client
import io.fleet.adminservice.data.ClientCredentials
import io.fleet.adminservice.data.User
import io.fleet.adminservice.data.UserCredentials
import javax.ws.rs.core.Response

interface KeycloakService {

    fun addRealm(name: String)

    fun addClient(realm: String, userCredentials: UserCredentials, client: Client) : Response

    fun addUser(realm: String, clientCredentials: ClientCredentials, userCredentials: UserCredentials, user: User) : Response

}
