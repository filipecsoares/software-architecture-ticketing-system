package com.fcs.admin.adapter.dataprovider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Habilita o uso de @PreAuthorize
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            // Converter roles padrão do Spring (scope)
            JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();
            authorities.addAll(defaultConverter.convert(jwt));

            // Obter o client que emitiu o token (azp)
            String azp = jwt.getClaimAsString("azp");

            // Apenas os tokens emitidos pelos clients permitidos serão considerados
            List<String> allowedClients = Arrays.asList("customer-client", "admin-client");

            if (azp != null && allowedClients.contains(azp)) {
                // Obtém as roles do client correto
                Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

                if (resourceAccess != null && resourceAccess.containsKey(azp)) {
                    Map<String, Object> clientRoles = (Map<String, Object>) resourceAccess.get(azp);
                    List<String> roles = (List<String>) clientRoles.get("roles");

                    if (roles != null) {
                        authorities.addAll(roles.stream()
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                .collect(Collectors.toList()));
                    }
                }
            }

            return authorities;
        });

        return converter;
    }
}
