package com.rtrivino.inventory.service;

import java.util.List;

import com.rtrivino.inventory.entity.Role;

public interface RoleService {
    Role save(Role role);
    List<Role> findAll();
    Role findById(Long id);
    void delete(Long id);
    Role update(Long id, Role role);
}
