package com.example.user_service.dao;

import com.example.user_service.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {
    void save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}