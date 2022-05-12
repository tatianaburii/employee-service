package com.tatianaburii.employeeservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DbConfiguration {
    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String user;
    @Value("${db.pass}")
    private String pass;


    @Bean
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}
