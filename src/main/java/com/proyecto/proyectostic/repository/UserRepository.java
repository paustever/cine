package com.proyecto.proyectostic.repository;

import com.proyecto.proyectostic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
