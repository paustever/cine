package com.proyecto.proyectostic.repository;


import com.proyecto.proyectostic.model.Movie;
import com.proyecto.proyectostic.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<ShowTime, Integer> {
    // Consulta para obtener todos los ShowTimes de una película
    @Query("SELECT s FROM ShowTime s WHERE s.movie = :movie")
    List<ShowTime> findByMovie(Movie movie);
    @Query("SELECT s FROM ShowTime s WHERE s.movie = :movie AND FUNCTION('DATE', s.showtimeDate) = :showtimeDate")
    List<ShowTime> findByMovieAndShowtimeDate(Movie movie, Date showtimeDate);



}

