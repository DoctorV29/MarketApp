package ru.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:application.yml")
public class Config {

    @Bean
    public Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:db/simple_database.db");
    }
}
