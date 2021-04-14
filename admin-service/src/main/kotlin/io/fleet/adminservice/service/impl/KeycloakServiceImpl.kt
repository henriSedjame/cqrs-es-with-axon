package io.fleet.adminservice.service.impl

import io.fleet.adminservice.configuration.KeycloakProvider
import io.fleet.adminservice.data.Client
import io.fleet.adminservice.data.ClientCredentials
import io.fleet.adminservice.data.User
import io.fleet.adminservice.data.UserCredentials
import io.fleet.adminservice.service.KeycloakService
import org.keycloak.admin.client.resource.ClientResource
import org.keycloak.representations.idm.ClientRepresentation
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.RealmRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Service
import java.util.*
import javax.ws.rs.core.Response

@Service
class KeycloakServiceImpl(val provider: KeycloakProvider) : KeycloakService {

    override fun addRealm(name: String) {
        RealmRepresentation().apply {
            realm = name
            isEnabled = true
        }.let {
            provider.of(null, null, null).realms().create(it)
        }
    }

    override fun addClient(realm: String,  userCredentials: UserCredentials, client: Client): Response {
        return ClientRepresentation().apply {
            clientId = client.id
            //secret = UUID.randomUUID().toString()
            isPublicClient = false
            isBearerOnly = false
            redirectUris = listOf(client.uri)
            isEnabled = true

        }.let {
            val clients = provider.of(realm, userCredentials).realm(realm).clients()
            val response = clients.create(it)

            val r = response.entity as CredentialRepresentation

            val resource = clients.get(r.id)

            resource.generateNewSecret()

            response
        }
    }

    override fun addUser(realm: String, clientCredentials: ClientCredentials, userCredentials: UserCredentials, user: User): Response {
       return UserRepresentation().apply {
            username = user.username
            firstName = user.firstname
            lastName = user.lastname
            email = user.email
            isEnabled = true
            isEmailVerified = false
            credentials = listOf(
                CredentialRepresentation().apply {
                    isTemporary = false
                    type = CredentialRepresentation.PASSWORD
                    value = user.password
                }
            )
        }.let { provider.of(realm, userCredentials, clientCredentials).realm(realm).users().create(it) }
    }


}
