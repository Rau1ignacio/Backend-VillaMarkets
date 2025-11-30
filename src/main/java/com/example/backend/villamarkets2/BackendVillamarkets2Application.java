package com.example.backend.villamarkets2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.backend")
@EnableJpaRepositories(basePackages = "com.example.backend.Repository")
@EntityScan(basePackages = "com.example.backend.model")
public class BackendVillamarkets2Application {

	public static void main(String[] args) {
		SpringApplication.run(BackendVillamarkets2Application.class, args);
	}

}

