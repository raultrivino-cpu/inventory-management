 package com.rtrivino.inventory.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rtrivino.inventory.entity.Role;
import com.rtrivino.inventory.exception.ElementNotFoundException;
import com.rtrivino.inventory.repository.RoleRepository;
import com.rtrivino.inventory.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Rol no encontrado"));
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role update(Long id, Role role) {
        Role roleDb = roleRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Rol no encontrado con id: " + id));

        roleDb.setNombre(role.getNombre());

        return roleRepository.save(roleDb);
    }

}
