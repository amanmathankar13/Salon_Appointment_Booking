package com.sab.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(
            exchange -> exchange
                .pathMatchers("/auth/**").permitAll()
                .pathMatchers("/notifications/ws/**").permitAll()
                .pathMatchers("/salons/**",
                "/category/**",
                "/notifications/**",
                "/bookings/**",
                "/payment/**",
                "/service-providing/**",
                "/user/**",
                "/reviews/**"
                ).hasAnyRole("CUSTOMER", "SALON_OWNER", "ADMIN")
                .pathMatchers("/category/salon-owner/**", "/notifications/salon-owner/**", "/service-providing/salon-owner/**").hasAnyRole("SALON_OWNER")
        ).oauth2ResourceServer(
            oAuth2ResourceServerSpec-> {
                oAuth2ResourceServerSpec.jwt(
                    jwtSpec-> jwtSpec.jwtAuthenticationConverter(grantAuthorityExtractor())
                );
            }
        );
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

    private Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> grantAuthorityExtractor() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(converter);
    }
}
