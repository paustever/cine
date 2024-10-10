package com.proyecto.proyectostic;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootApplication
public class ProyectosticApplication {

    @Value("${spring.application.name}")
    private String applicationName;

    public static void main(String[] args) {
        SpringApplication.run(ProyectosticApplication.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println("Application Name: " + applicationName);
    }
}

