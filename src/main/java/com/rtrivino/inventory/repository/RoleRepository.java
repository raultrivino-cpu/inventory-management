package com.rtrivino.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtrivino.inventory.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
