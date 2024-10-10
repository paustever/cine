package com.proyecto.proyectostic.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) ; // Deshabilita CSRF para pruebas con Postman
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/users/register", "/users/login").permitAll()  // Permitir el registro y login sin autenticación
//                        .requestMatchers("/billboards/**").permitAll()  // Permitir acceso a los endpoints de 'billboards'
//                        .anyRequest().authenticated()  // Requiere autenticación para las demás rutas
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/users/logout")
//                        .logoutSuccessHandler((request, response, authentication) -> {
//                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);  // 204 No Content en el logout
//                        })
//                );
        return http.build();
    }
}
