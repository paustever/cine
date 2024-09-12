package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.excepcion.InvalidCredentialsException;
import com.proyecto.proyectostic.excepcion.InvalidPasswordException;
import com.proyecto.proyectostic.excepcion.UserAlreadyExistsException;
import com.proyecto.proyectostic.excepcion.UserNotFoundException;
import com.proyecto.proyectostic.model.User;
import com.proyecto.proyectostic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public Optional<User> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;  // Login exitoso
        } else {
            throw new InvalidCredentialsException("Invalid email or password");  // Lanzar excepción si las credenciales son incorrectas
        }
    }

    public User updateUser(Integer id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setLastName(updatedUser.getLastName());
                    user.setEmail(updatedUser.getEmail());
                    user.setTelephone(updatedUser.getTelephone());
                    // Actualiza cualquier otro campo necesario
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }

    public Optional<User> getProfile(Integer id) {
        return userRepository.findById(id);
    }
}

