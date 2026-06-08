package com.rtrivino.inventory.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtrivino.inventory.dto.UserDto;
import com.rtrivino.inventory.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller responsible for authentication-related operations.
 *
 * <p>
 * This controller exposes endpoints used by the frontend to retrieve
 * information about the currently authenticated user. Authentication itself is
 * handled by Spring Security using Basic Auth; this controller only returns the
 * user information after the credentials have already been validated.
 * </p>
 *
 * <p>
 * The main purpose of this controller is to support the frontend login flow.
 * Once the user provides valid credentials, the frontend calls this endpoint to
 * obtain the authenticated user's login and role.
 * </p>
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * Returns the currently authenticated user.
     *
     * <p>
     * The login is obtained from the {@link Authentication} object populated
     * by Spring Security. This avoids requiring the client to send the username
     * again in the request body or as a query parameter.
     * </p>
     *
     * @param authentication Spring Security authentication context containing
     *                       the authenticated principal
     * @return the authenticated user's information, including login and role
     */
    @GetMapping("/me")
    public UserDto me(Authentication authentication) {
        String login = authentication.getName();
        return userService.findByLogin(login);
    }
}