package io.fleet.securityconfig

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(SecurityConfiguration::class)
annotation class SecuredResourceServer()
