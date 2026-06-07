package com.rtrivino.inventory.mapper;

import org.springframework.stereotype.Component;

import com.rtrivino.inventory.dto.UserDto;
import com.rtrivino.inventory.entity.Role;
import com.rtrivino.inventory.entity.User;
import com.rtrivino.inventory.exception.ElementNotFoundException;
import com.rtrivino.inventory.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleRepository roleRepository;

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getRol() != null ? user.getRol().getNombre() : null
        );
    }

    public User toEntity(UserDto user){
        Role role = roleRepository.findById(Long.valueOf(user.getRol()))
            .orElseThrow(() -> new ElementNotFoundException("Rol no encontrado"));
 
        return new User(user.getId(), user.getLogin(), user.getPassword(), role);
    }
}