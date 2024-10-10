package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.excepcion.InvalidCredentialsException;
import com.proyecto.proyectostic.excepcion.InvalidPasswordException;
import com.proyecto.proyectostic.excepcion.UserAlreadyExistsException;
import com.proyecto.proyectostic.excepcion.UserNotFoundException;
import com.proyecto.proyectostic.model.Reservation;
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
    public ResponseEntity<User> updateUser(
            @PathVariable Integer id,
            @RequestBody User updatedUser,
            @RequestHeader("Authorization") String token) {
        try {
            User user = userService.updateUser(id, updatedUser, token);
            return ResponseEntity.ok(user);  // Devuelve 200 OK si la actualización es exitosa
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(401).build();  // Devuelve 401 Unauthorized si el token es inválido o el usuario no tiene permiso
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).build();  // Devuelve 404 si no se encuentra al usuario
        }
    }



    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return ResponseEntity.ok(newUser);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(409).build();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return ResponseEntity.status(500).build(); // Return a generic 500 error in case something unexpected happens
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(@RequestHeader("Authorization") String token) {
        try {
            userService.logoutUser(token);
            return ResponseEntity.noContent().build();  // Devuelve 204 No Content si el logout es exitoso
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(401).build();  // Devuelve 401 si el token es inválido
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        try {
            String token = userService.loginUser(email, password);
            return ResponseEntity.ok(token);  // Devuelve el token si el login es exitoso
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(401).build();  // Devuelve 401 si las credenciales son incorrectas
        }
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<User> getUserProfile(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {
        try {
            User userProfile = userService.getProfile(id, token)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
            return ResponseEntity.ok(userProfile);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(401).build();  // Devuelve 401 si el token es inválido
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).build();  // Devuelve 404 si no se encuentra el usuario
        }
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity<List<Reservation>> getAllReservationsForUser(@PathVariable Integer id) {
        List<Reservation> reservations = userService.showAllReservationForUser(id);
        return ResponseEntity.ok(reservations);
    }
}
