package com.example.user_service.controller;

import com.example.user_service.dto.UserDTO;
import com.example.user_service.model.Role;
import com.example.user_service.model.User;
import com.example.user_service.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User newUser) {
        String result = userService.registerUser(newUser);
        return result.equals("Usuario registrado con exito!") ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        System.out.println("🔹 Received Username: [" + username + "]");
        System.out.println("🔹 Received Password: [" + password + "]");
        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Missing username or password"));
        }
        String token = userService.loginUser(username, password);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid username or password"));
        }
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }


    @GetMapping("/userinfo")
    public ResponseEntity<UserDTO> getUserDetails(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Optional<UserDTO> user = userService.getUserByUsername(userService.getUsernameFromToken(token));

        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/userlist")
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }


    @PutMapping("/userlist/{id}/promote")
    public ResponseEntity<?> promoteUserToAdmin(@PathVariable Long id) {
        Optional<UserDTO> userOptional = userService.getUserById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        Optional<User> user = userService.getUserEntityById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (user.get().getRole() == Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is already an admin");
        }
        user.get().setRole(Role.ADMIN);
        userService.saveUser(user.get());

        return ResponseEntity.ok(Collections.singletonMap("message", "User promoted to admin successfully"));
    }
}
