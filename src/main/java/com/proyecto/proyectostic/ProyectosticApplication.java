package com.proyecto.proyectostic;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ProyectosticApplication {

    public static void main(String[] args) {
        File envFile = new File(".env");

        if (envFile.exists()) {
            // Load .env for development mode
            Dotenv dotenv = Dotenv.configure().load();
            System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
            System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
            System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
            System.setProperty("DB_USER", dotenv.get("DB_USER"));
            System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        } else {
            // Load environment variables for production mode
            String dbHost = System.getenv("DB_HOST");
            String dbPort = System.getenv("DB_PORT");
            String dbName = System.getenv("DB_NAME");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");

            System.setProperty("DB_HOST", dbHost);
            System.setProperty("DB_PORT", dbPort);
            System.setProperty("DB_NAME", dbName);
            System.setProperty("DB_USER", dbUser);
            System.setProperty("DB_PASSWORD", dbPassword);
        }

        SpringApplication.run(ProyectosticApplication.class, args);

    }

}
