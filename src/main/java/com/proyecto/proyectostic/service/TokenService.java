package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import com.proyecto.proyectostic.repository.UserRepository;
import java.security.Key;
import java.util.Date;


@Service
public class TokenService {
    private Key key;
    private final UserRepository userRepository;
    private final Set<String> blacklistedTokens = new HashSet<>(); // Lista negra en memoria

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        // Genera automáticamente una clave segura para HMAC-SHA256
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }



    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail()) // o user.getUserId()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora de expiración
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValidToken(String token) {
        return !blacklistedTokens.contains(token) && verifyToken(token);
    }

    private boolean verifyToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<User> getUserFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            String email = claims.getSubject(); // Extrae el email del token
            return userRepository.findByEmail(email); // Encuentra el usuario por su email
        } catch (Exception e) {
            return Optional.empty(); // Token inválido o usuario no encontrado
        }
    }

    public void invalidateToken(String token) {
        // Añade el token a la lista negra
        blacklistedTokens.add(token);
    }
}

