package com.example.user_service;

import com.example.user_service.config.StartupService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(UserServiceApplication.class, args);

		StartupService startupService = context.getBean(StartupService.class);
		startupService.createDefaultAdmin();
	}
}
