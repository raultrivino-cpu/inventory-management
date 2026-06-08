package com.rtrivino.inventory.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.rtrivino.inventory.entity.User;
import com.rtrivino.inventory.exception.ElementNotFoundException;
import com.rtrivino.inventory.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Custom implementation of Spring Security's {@link UserDetailsService}.
 *
 * <p>
 * This service is responsible for loading application users from the database
 * during the authentication process. Spring Security calls this class when a
 * client sends Basic Auth credentials.
 * </p>
 *
 * <p>
 * The user is searched by login and its role is loaded together with the user
 * so it can be converted into a Spring Security authority. This authority is
 * later used by the security configuration to restrict access to endpoints
 * based on roles such as {@code ROLE_ADMIN} and {@code ROLE_EXTERNAL}.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository usuarioRepository;

    /**
     * Loads a user by login and adapts it to Spring Security's {@link UserDetails}.
     *
     * <p>
     * The returned object contains the login, encrypted password and granted
     * authorities required by Spring Security to authenticate and authorize the
     * request.
     * </p>
     *
     * @param username login provided by the client through Basic Auth
     * @return Spring Security user details containing credentials and authorities
     * @throws ElementNotFoundException when the user does not exist in the database
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User usuario = usuarioRepository.findByLoginWithRol(username)
                .orElseThrow(() -> new ElementNotFoundException("Usuario no encontrado"));

        return new org.springframework.security.core.userdetails.User(
                usuario.getLogin(),
                usuario.getContrasena(),
                List.of(new SimpleGrantedAuthority(usuario.getRol().getNombre())));
    }
}