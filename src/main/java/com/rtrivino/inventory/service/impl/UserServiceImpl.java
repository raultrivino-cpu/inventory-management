package com.rtrivino.inventory.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rtrivino.inventory.dto.UserDto;
import com.rtrivino.inventory.entity.User;
import com.rtrivino.inventory.exception.ElementNotFoundException;
import com.rtrivino.inventory.mapper.UserMapper;
import com.rtrivino.inventory.repository.UserRepository;
import com.rtrivino.inventory.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation responsible for user business operations.
 *
 * <p>
 * This service manages application users and delegates entity/DTO conversion
 * to the user mapper. It also applies password encryption using BCrypt before
 * storing or updating user credentials.
 * </p>
 *
 * <p>
 * When updating a user, the password is only re-encoded if a non-empty value
 * is provided, allowing user data to be updated without forcing a password
 * change.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public User save(UserDto user) {
        User userEntity = userMapper.toEntity(user);

        userEntity.setContrasena(passwordEncoder.encode(userEntity.getContrasena()));
        return userRepository.save(userEntity);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Usuario no encontrado"));

        return userMapper.toDto(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto update(Long id, UserDto usuario) {
        User userDb = userRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Usuario no encontrado con id: " + id));

        User userEntity = userMapper.toEntity(usuario);

        userDb.setLogin(userEntity.getLogin());
        userDb.setRol(userEntity.getRol());

        if (userEntity.getContrasena() != null &&
                !userEntity.getContrasena().isBlank()) {

            userDb.setContrasena(
                    passwordEncoder.encode(userEntity.getContrasena()));
        }

        User saved = userRepository.save(userDb);
        return userMapper.toDto(saved);
    }

    @Override
    public UserDto findByLogin(String login) {
        User userDb = userRepository.findByLoginWithRol(login)
                .orElseThrow(() -> new ElementNotFoundException("Usuario no encontrado con login: " + login));

        return userMapper.toDto(userDb);
    }

}
