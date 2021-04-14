package io.fleet.securityconfig

import net.minidev.json.JSONArray
import net.minidev.json.JSONObject
import org.springframework.context.annotation.Bean
import org.springframework.core.convert.converter.Converter
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.stream.Collectors

import org.springframework.security.core.authority.SimpleGrantedAuthority

import org.springframework.security.core.GrantedAuthority

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter

import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders

import reactor.core.publisher.Mono


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfiguration {

    companion object {
        const val REALM_ACCESS = "realm_access"
        const val ROLES = "roles"
    }

    @Bean
    fun securityFilterChain(httpSecurity: ServerHttpSecurity, securityRules: ApiSecurityRules) : SecurityWebFilterChain {
        return httpSecurity
            .csrf {
                it.disable()
            }
            .cors {
                it.configurationSource(SecurityUtils.corsConfiguration())
            }
            .formLogin {
                it.disable()
            }
            .authorizeExchange {
                it.pathMatchers(HttpMethod.OPTIONS).permitAll()
                securityRules.permitAll?.forEach { link -> it.pathMatchers(link).permitAll() }
                securityRules.permitByRole?.forEach { (auth, links) ->
                    links.forEach { link -> it.pathMatchers(link).hasAuthority(auth) }
                }
                it.anyExchange().authenticated()
            }
            .oauth2ResourceServer {
                it.jwt().jwtAuthenticationConverter(grantedAuthoritiesExtractor())
            }
            .build()
    }

    @Bean
    fun jwtDecoder(authServer: AuthServer): ReactiveJwtDecoder = ReactiveJwtDecoders.fromOidcIssuerLocation(authServer.url)

    private fun grantedAuthoritiesExtractor(): Converter<Jwt?, Mono<AbstractAuthenticationToken?>?> {
        val extractor = GrantedAuthoritiesExtractor()
        return ReactiveJwtAuthenticationConverterAdapter(extractor)
    }

    internal class GrantedAuthoritiesExtractor : JwtAuthenticationConverter() {
        override fun extractAuthorities(jwt: Jwt): Collection<GrantedAuthority> {
            val claims: Map<String, Any> = jwt.claims
            val realmAccess: JSONObject? = claims[REALM_ACCESS] as JSONObject?
            val roles: JSONArray = realmAccess?.get(ROLES) as JSONArray
            return roles.stream()
                .map { obj: Any -> obj.toString() }
                .map { role: String? -> SimpleGrantedAuthority(role) }
                .collect(Collectors.toSet())
        }
    }
}
