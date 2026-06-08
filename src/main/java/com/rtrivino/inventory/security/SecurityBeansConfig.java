package com.rtrivino.inventory.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

/**
 * Configuration class that declares the authentication-related Spring Security
 * beans.
 *
 * <p>
 * This class separates reusable security beans from the HTTP authorization
 * configuration. It provides the password encoder used to hash and verify user
 * passwords, as well as the authentication provider used by Spring Security to
 * validate credentials against users stored in the database.
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class SecurityBeansConfig {
    private final CustomUserDetailsService userDetailsService;

    /**
     * Provides the password encoder used by the application.
     *
     * <p>
     * BCrypt is used to securely hash user passwords before storing them in
     * the database and to verify raw credentials during authentication.
     * </p>
     *
     * @return BCrypt password encoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the authentication provider used by Spring Security.
     *
     * <p>
     * The {@link DaoAuthenticationProvider} delegates user lookup to the
     * custom {@link CustomUserDetailsService} and password verification to the
     * configured {@link PasswordEncoder}.
     * </p>
     *
     * @return authentication provider configured for database-backed users
     */
    @Bean
    AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }
}
