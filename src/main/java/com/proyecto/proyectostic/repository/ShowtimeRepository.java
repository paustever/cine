package com.proyecto.proyectostic.repository;


import com.proyecto.proyectostic.model.Cinema;
import com.proyecto.proyectostic.model.Movie;
import com.proyecto.proyectostic.model.ShowTime;
import com.proyecto.proyectostic.model.ShowTimeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ShowtimeRepository extends JpaRepository<ShowTime, ShowTimeId> {
    // Consulta para obtener todos los ShowTimes de una película
    @Query("SELECT s FROM ShowTime s WHERE s.movie = :movie")
    List<ShowTime> findByMovie(Movie movie);
    @Query("SELECT s FROM ShowTime s WHERE s.movie = :movie AND FUNCTION('DATE', s.showtimeDate) = :showtimeDate")
    List<ShowTime> findByMovieAndShowtimeDate(Movie movie, Date showtimeDate);
    @Query("SELECT s FROM ShowTime s WHERE s.movie = :movie AND s.billboard.cinema = :cinema AND FUNCTION('DATE', s.showtimeDate) = :showtimeDate")
    List<ShowTime> findByMovieAndCinemaAndShowtimeDate(Movie movie, Cinema cinema, Date showtimeDate);
    @Query("SELECT s FROM ShowTime s WHERE s.movie = :movie AND s.billboard.cinema = :cinema")
    List<ShowTime> findByMovieAndCinema(Movie movie, Cinema cinema);
    @Query("SELECT s FROM ShowTime s WHERE s.movie.movieId = :movieId AND s.room.roomId = :roomId AND s.showtimeDate = :showtimeDate")
    Optional<ShowTime> findByMovieRoomAndDate(@Param("movieId") Integer movieId, @Param("roomId") Integer roomId, @Param("showtimeDate") Date showtimeDate);

}

