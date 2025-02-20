package com.example.user_service;

import com.example.user_service.config.StartupService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;

@SpringBootApplication
public class UserServiceApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(UserServiceApplication.class, args);

		System.out.println("🔹 Registered Controllers:");
		Arrays.stream(context.getBeanDefinitionNames())
				.filter(name -> name.toLowerCase().contains("controller"))
				.forEach(System.out::println);

		try {
			RequestMappingHandlerMapping mapping = context.getBean(RequestMappingHandlerMapping.class);
			System.out.println("\n🔍 REGISTERED ENDPOINTS:");
			mapping.getHandlerMethods().forEach((key, value) -> System.out.println("➡️ " + key));
		} catch (Exception e) {
			System.out.println("⚠️ Could not retrieve request mappings.");
		}

		// Create default admin
		StartupService startupService = context.getBean(StartupService.class);
		startupService.createDefaultAdmin();
	}
}
