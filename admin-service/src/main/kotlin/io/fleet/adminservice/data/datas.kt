package io.fleet.adminservice.data

data class Client(
    val id: String,
    val uri: String)

data class ClientCredentials(
    val id: String,
    val secret: String)

data class User(
    val username: String?,
    val firstname: String?,
    val lastname: String?,
    val email: String?,
    val password: String?)

data class UserCredentials(
    val username: String,
    val password: String)

fun defaultClientCedentials() : ClientCredentials  = ClientCredentials("admin-service", "1da99444-e240-4a31-9d84-352674bdb953")
