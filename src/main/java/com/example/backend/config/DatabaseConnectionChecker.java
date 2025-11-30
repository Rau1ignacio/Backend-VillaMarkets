package com.example.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DatabaseConnectionChecker implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionChecker.class);
    private final JdbcTemplate jdbcTemplate;

    public DatabaseConnectionChecker(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            if (result != null && result == 1) {
                logger.info("Conexión a la base de datos exitosa (SELECT 1 = {}).", result);
            } else {
                logger.warn("Conexión a la base de datos respondió inesperadamente: {}", result);
            }
        } catch (Exception e) {
            logger.error("Error de conexion a la base de datos: {}", e.getMessage(), e);
        }
    }
}
