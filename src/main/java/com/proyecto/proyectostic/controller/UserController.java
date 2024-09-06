package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.excepcion.InvalidCredentialsException;
import com.proyecto.proyectostic.excepcion.InvalidPasswordException;
import com.proyecto.proyectostic.excepcion.UserAlreadyExistsException;
import com.proyecto.proyectostic.excepcion.UserNotFoundException;
import com.proyecto.proyectostic.model.User;
import com.proyecto.proyectostic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        try {
            User user = userService.updateUser(id, updatedUser);
            return ResponseEntity.ok(user);  // Devuelve el usuario actualizado con 200 OK
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();  // Si no se encuentra, devuelve 404
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.status(201).body(registeredUser);  // 201 Created si el usuario se registra
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(409).build();  // 409 Conflict si el usuario ya existe
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestParam String email, @RequestParam String password) {
        try {
            User loggedInUser = userService.loginUser(email, password).get();
            return ResponseEntity.ok(loggedInUser);  // Devuelve 200 OK si el login es exitoso
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(401).build();  // Devuelve 401 Unauthorized si las credenciales son incorrectas
        }
    }
    @GetMapping("/{id}/profile")
    public ResponseEntity<User> getUserProfile(@PathVariable Integer id) {
        Optional<User> user = userService.getProfile(id);
        // Si el usuario está presente, devolvemos 200 OK con el objeto User
        return user.map(ResponseEntity::ok)
                // Si el usuario no está presente, devolvemos 404 Not Found
                .orElse(ResponseEntity.notFound().build());
    }

}
