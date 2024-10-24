package com.proyecto.proyectostic.repository;

import com.proyecto.proyectostic.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
        boolean existsByName(String name);

}

