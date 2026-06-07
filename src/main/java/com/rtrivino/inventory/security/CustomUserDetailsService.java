package com.rtrivino.inventory.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rtrivino.inventory.entity.User;
import com.rtrivino.inventory.exception.ElementNotFoundException;
import com.rtrivino.inventory.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService { 

    private final UserRepository usuarioRepository;
        
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