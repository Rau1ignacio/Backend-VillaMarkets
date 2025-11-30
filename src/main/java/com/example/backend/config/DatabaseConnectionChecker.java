package com.example.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectionChecker implements CommandLineRunner {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        try {
            jdbcTemplate.execute("SELECT 1");
            System.out.println("\u2705 Conexión a la base de datos exitosa");
        } catch (Exception e) {
            System.out.println("\u274C Error de conexión a la base de datos: " + e.getMessage());
        }
    }
}
