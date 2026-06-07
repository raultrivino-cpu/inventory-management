package com.rtrivino.inventory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String login;
    private String rol;
    private String password;
    
    public UserDto() {
    }

    public UserDto(Long id, String login, String rol) {
        this.id = id;
        this.login = login;
        this.rol = rol;
    }

    public UserDto(Long id, String login, String rol, String password) {
        this.id = id;
        this.login = login;
        this.rol = rol;
        this.password = password;
    }
}
