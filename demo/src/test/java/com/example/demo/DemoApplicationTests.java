package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers // Enables Testcontainers for JUnit 5
class DemoApplicationTests {

    // Define the PostgreSQL container
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    // Dynamically set Spring properties to connect to the Testcontainer
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        // If you use Spring Session JDBC, you might need these too:
        registry.add("spring.session.store-type", () -> "jdbc");
        registry.add("spring.session.jdbc.initialize-schema", () -> "always"); // Or 'create'
    }

    @Test
    void contextLoads() {
        // Your application context will now start with a connection to the Testcontainer DB
    }

}