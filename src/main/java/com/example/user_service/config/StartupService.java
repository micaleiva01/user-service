package com.example.user_service.config;

import com.example.user_service.dao.IUserDAO;
import com.example.user_service.model.Role;
import com.example.user_service.model.User;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class StartupService {

    private final IUserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public StartupService(IUserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    //@Transactional
    public void createDefaultAdmin() {
        System.out.println("Revisando si ya existe un admin default...");

        if (userDAO.findByUsername("admin").isPresent()) {
            System.out.println("Admin ya existe. No se creara un default nuevo");
            return;
        }

        System.out.println("Creando admin default...");

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(Role.ADMIN);

        userDAO.save(admin);
        System.out.println("Admin creado!");
    }
}

