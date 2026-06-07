package com.rtrivino.inventory.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/empresas/**")
                    .hasAnyRole("ADMIN", "EXTERNAL")
                .requestMatchers(HttpMethod.POST, "/api/empresas/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/empresas/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/empresas/**")
                    .hasRole("ADMIN")
                .requestMatchers("/api/productos/**")
                    .hasRole("ADMIN")
                .requestMatchers("/api/categorias/**")
                    .hasRole("ADMIN")
                .requestMatchers("/api/clientes/**")
                    .hasRole("ADMIN")
                .requestMatchers("/api/ordenes/**")
                    .hasRole("ADMIN")
                .requestMatchers("/api/roles/**")
                    .hasRole("ADMIN")
                .requestMatchers("/api/usuarios/**")
                    .hasRole("ADMIN")
                .anyRequest()
                    .authenticated()
            )
            .httpBasic(httpBasic -> {});

        return http.build();
    }
}