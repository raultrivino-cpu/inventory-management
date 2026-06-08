package com.rtrivino.inventory.controller;

import org.springframework.web.bind.annotation.RestController;

import com.rtrivino.inventory.entity.Role;
import com.rtrivino.inventory.service.RoleService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * REST controller responsible for role management operations.
 *
 * <p>
 * Roles define the authorization level of users within the application.
 * The system uses roles such as {@code ROLE_ADMIN} and {@code ROLE_EXTERNAL}
 * to control access to administrative and read-only features.
 * </p>
 *
 * <p>
 * This controller exposes standard CRUD endpoints for roles. These endpoints
 * are intended for administrative use and are protected through Spring
 * Security.
 * </p>
 */
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public Role findById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Role create(@RequestBody Role role) {
        return roleService.save(role);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }

    @PutMapping("/{id}")
    public Role update(@PathVariable Long id, @RequestBody Role entity) {
        return roleService.update(id, entity);
    }
}
