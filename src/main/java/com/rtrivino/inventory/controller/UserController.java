package com.rtrivino.inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rtrivino.inventory.dto.UserDto;
import com.rtrivino.inventory.entity.User;
import com.rtrivino.inventory.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller responsible for user management operations.
 *
 * <p>
 * Users are stored in the database and authenticated through Spring Security.
 * Each user is associated with a role that determines the operations available
 * in the system.
 * </p>
 *
 * <p>
 * This controller exposes standard CRUD endpoints for managing application
 * users. Password handling, including BCrypt encryption, is delegated to the
 * service layer.
 * </p>
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody UserDto usuario) {
        return userService.save(usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDto usuario) {
        return userService.update(id, usuario);
    }
}
