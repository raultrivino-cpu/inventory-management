package com.rtrivino.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rtrivino.inventory.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    @Query("SELECT u FROM User u JOIN FETCH u.rol WHERE u.login = :login")
    Optional<User> findByLoginWithRol(String login);
}
