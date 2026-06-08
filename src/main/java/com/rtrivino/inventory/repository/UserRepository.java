package com.rtrivino.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rtrivino.inventory.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by login and eagerly loads the assigned role.
     *
     * <p>
     * This query is used during authentication to ensure that the user's
     * authorities are available when Spring Security builds the authenticated
     * principal.
     * </p>
     *
     * @param login user login
     * @return user with its assigned role, if found
     */
    @Query("SELECT u FROM User u JOIN FETCH u.rol WHERE u.login = :login")
    Optional<User> findByLoginWithRol(String login);
}
