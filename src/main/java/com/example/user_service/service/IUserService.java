package com.example.user_service.service;

import com.example.user_service.dto.UserDTO;
import com.example.user_service.model.Role;
import com.example.user_service.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    String registerUser(User user);
    String loginUser(String username, String password);
    Optional<UserDTO> getUserById(Long id);
    Optional<UserDTO> getUserByUsername(String username);
    Optional<User> getUserEntityById(Long id);
    List<UserDTO> getAllUsers();
    String getUsernameFromToken(String token);
    void saveUser(User user);
    Optional<User> getUserEntityByUsername(String usernameFromToken);
}
