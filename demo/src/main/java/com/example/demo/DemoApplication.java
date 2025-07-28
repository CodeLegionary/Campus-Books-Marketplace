package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.demo")
public class DemoApplication {
	public static void main(String[] args) {
		//System.out.println("POSTGRES_USER: " + System.getenv("POSTGRES_USER"));
		//System.out.println("POSTGRES_PASSWORD: " + System.getenv("POSTGRES_PASSWORD"));
		SpringApplication.run(DemoApplication.class, args);
	}
}

