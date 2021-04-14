package io.fleet.adminservice.configuration

import io.fleet.adminservice.configuration.properties.KeycloakProperties
import io.fleet.adminservice.data.ClientCredentials
import io.fleet.adminservice.data.UserCredentials
import io.fleet.adminservice.data.defaultClientCedentials
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class KeycloakProvider(val properties: KeycloakProperties) {

    fun of(realm: String?=null,  userCredentials: UserCredentials?, clientCredentials: ClientCredentials? = defaultClientCedentials(), ) : Keycloak = KeycloakBuilder.builder()
        .serverUrl(properties.serverUrl)
        .realm(realm)
        .username(userCredentials?.username)
        .password(userCredentials?.password)
        .clientId(clientCredentials?.id)
        .clientSecret(clientCredentials?.secret)
        .grantType(OAuth2Constants.PASSWORD)
        .resteasyClient(
            ResteasyClientBuilder()
                .connectionPoolSize(10)
                .build())
        .build()
}
