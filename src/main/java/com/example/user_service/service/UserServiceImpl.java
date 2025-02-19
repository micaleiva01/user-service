package com.example.user_service.service;

import com.example.user_service.dao.IUserDAO;
import com.example.user_service.dto.UserDTO;
import com.example.user_service.model.Role;
import com.example.user_service.model.User;
import com.example.user_service.config.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private JwtUtil jwtUtil;

    @PostConstruct
    public void checkJwtUtilInjection() {
        System.out.println("JwtUtil instance: " + jwtUtil);
    }

    @Override
    public String registerUser(User user) {
        if (userDAO.findByUsername(user.getUsername()).isPresent() ||
                userDAO.findByEmail(user.getEmail()).isPresent()) {
            return "Username or email already taken";
        }

        if (!user.getPassword().startsWith("$2a$10$")) { // BCrypt hashes start with "$2a$10$"
            System.out.println("Raw Password Before Hashing: [" + user.getPassword() + "]");
            user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode only if it's not already hashed
            System.out.println("Hashed Password: [" + user.getPassword() + "]");
        } else {
            System.out.println("Warning: Password is already hashed, skipping encoding.");
        }
        userDAO.save(user);
        return "User registered successfully";
    }


    @Override
    public String loginUser(String username, String password) {
        Optional<User> userOptional = userDAO.findByUsername(username);

        if (userOptional.isEmpty()) {
            return "Ingresar nombre de usuario o contraseña";
        }

        User user = userOptional.get();

        // Check password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Nombre de usuario o contraseña invalida";
        }

        // 🔹 Ensure JWT is being generated
        String token = jwtUtil.generateToken(user);
        System.out.println("🔹 Generated Token: " + token);  // Debugging

        return token;
    }



    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userDAO.findById(id).map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole()));
    }

    @Override
    public Optional<UserDTO> getUserByUsername(String username) {
        return userDAO.findByUsername(username).map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole()));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userDAO.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsernameFromToken(String token) {
        return jwtUtil.extractUsername(token);
    }
}
