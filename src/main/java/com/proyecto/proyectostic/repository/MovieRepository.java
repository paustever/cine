package com.proyecto.proyectostic.repository;

import com.proyecto.proyectostic.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
