package com.rtrivino.inventory.service;

import java.util.List;

import com.rtrivino.inventory.dto.UserDto;
import com.rtrivino.inventory.entity.User;

public interface UserService {
    User save(UserDto user);
    List<UserDto> findAll();
    UserDto findById(Long id);
    void delete(Long id);
    UserDto update(Long id, UserDto usuario);
}
