package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.excepcion.InvalidCredentialsException;
import com.proyecto.proyectostic.excepcion.InvalidPasswordException;
import com.proyecto.proyectostic.excepcion.UserAlreadyExistsException;
import com.proyecto.proyectostic.excepcion.UserNotFoundException;
import com.proyecto.proyectostic.model.Reservation;
import com.proyecto.proyectostic.model.User;
import com.proyecto.proyectostic.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class UserService {

    private final UserRepository userRepository;  //El UserRepositoryes una interfaz en Spring Boot que permite interactuar directamente con la base de datos para realizar operaciones CRUD
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(TokenService tokenService, UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // Guardar el usuario en la base de datos
        User savedUser = userRepository.save(user);
        // Devolver el usuario sin la contraseña
        return savedUser;
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public String loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            String token = tokenService.generateToken(user.get());
            return token;  // Devuelve el token generado
        } else {
            throw new InvalidCredentialsException("Invalid email or password");  // Lanzar excepción si las credenciales son incorrectas
        }
    }

    public void logoutUser(String token) {
        tokenService.invalidateToken(token);  // Invalida el token proporcionado
    }


    public User updateUser(Integer id, User updatedUser, String token) {
        // Verifica si el token es válido y extrae el usuario del token
        String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        Optional<User> userFromToken = tokenService.getUserFromToken(actualToken);

        // Verifica si el token es válido y si el usuario tiene permiso
        User user = userFromToken.orElseThrow(() -> new InvalidCredentialsException("Invalid token"));
        if (!user.getUserId().equals(id)) {
            throw new InvalidCredentialsException("Unauthorized action");
        }

        // Actualiza el usuario si pasa todas las validaciones
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(updatedUser.getName());
                    existingUser.setLastName(updatedUser.getLastName());
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setTelephone(updatedUser.getTelephone());
                    // Actualiza otros campos necesarios
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }


    public Optional<User> getProfile(Integer id, String token) {
        // Verifica si el token es válido y extrae el usuario del token
        String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        Optional<User> userFromToken = tokenService.getUserFromToken(actualToken);

        // Verifica si el token es válido y si el usuario tiene permiso
        User user = userFromToken.orElseThrow(() -> new InvalidCredentialsException("Invalid token"));
        if (!user.getUserId().equals(id)) {
            throw new InvalidCredentialsException("Unauthorized action");
        }

        // Devuelve el perfil del usuario
        return userRepository.findById(id);
    }





}

