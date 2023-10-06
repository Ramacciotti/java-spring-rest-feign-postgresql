package com.ramacciotti.external.api.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String personname;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public Connection connect() {

        log.info("** Trying to connect to the PostgreSQL database...");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(url, personname, password);

            log.info("** Connected successfully!");

        } catch (SQLException e) {

            log.error("## Ops! Couldn´t connect: {}", e.getMessage());

        }

        return connection;

    }

}
