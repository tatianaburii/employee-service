package com.tatianaburii.employeeservice.config;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;

@Configuration
public class TestDbConfiguration {

    @MockBean
    public Connection connection;

}