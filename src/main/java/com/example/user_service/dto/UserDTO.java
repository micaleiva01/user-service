package com.example.user_service.dto;

import com.example.user_service.model.Role;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Role role;

    public UserDTO(Long id, String username, String email, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
}
