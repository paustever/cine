package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.model.Cinema;
import com.proyecto.proyectostic.model.Movie;
import com.proyecto.proyectostic.model.ShowTime;
import com.proyecto.proyectostic.service.CinemaService;
import com.proyecto.proyectostic.service.MovieService;
import com.proyecto.proyectostic.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {

    private final ShowtimeService showtimeService;
    private final MovieService movieService;
    private final CinemaService cinemaService;

    @Autowired
    public ShowtimeController(ShowtimeService showtimeService, MovieService movieService, CinemaService cinemaService) {
        this.showtimeService = showtimeService;
        this.movieService = movieService;
        this.cinemaService = cinemaService;
    }

    // Obtener todos los horarios
    @GetMapping
    public ResponseEntity<List<ShowTime>> getAllShowtimes() {
        List<ShowTime> showtimes = showtimeService.getAllShowtimes();
        return ResponseEntity.ok(showtimes);
    }

    // Crear un nuevo horario
    @PostMapping
    public ResponseEntity<ShowTime> createShowtime(@RequestBody ShowTime showtime) {
        ShowTime createdShowtime = showtimeService.saveShowtime(showtime);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShowtime);
    }

    // Obtener horarios de una película agrupados por cines
    @GetMapping("/by-movie/{movieId}")
    public ResponseEntity<Map<Cinema, List<ShowTime>>> getShowTimesByMovie(@PathVariable Integer movieId) {
        Movie movie = movieService.findById(movieId);
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Map<Cinema, List<ShowTime>> showtimesByCinema = showtimeService.getShowTimesByMovie(movie);
        return ResponseEntity.ok(showtimesByCinema);
    }

    // Obtener fechas disponibles para una película en un cine específico
    @GetMapping("/by-movie-cinema/{movieId}/{cinemaId}")
    public ResponseEntity<List<Date>> getAvailableDatesByMovieAndCinema(@PathVariable Integer movieId, @PathVariable Integer cinemaId) {
        Movie movie = movieService.findById(movieId);
        Cinema cinema = cinemaService.findById(cinemaId);

        if (movie == null || cinema == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Date> availableDates = showtimeService.getAvailableDatesByMovieAndCinema(movie, cinema);
        return ResponseEntity.ok(availableDates);
    }

    // Obtener horarios de una película en un cine y una fecha específica
    @GetMapping("/by-movie-cinema-date/{movieId}/{cinemaId}/{date}")
    public ResponseEntity<List<ShowTime>> getShowTimesByMovieCinemaAndDate(
            @PathVariable Integer movieId,
            @PathVariable Integer cinemaId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        Movie movie = movieService.findById(movieId);
        Cinema cinema = cinemaService.findById(cinemaId);

        if (movie == null || cinema == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<ShowTime> showTimes = showtimeService.getShowTimesByMovieCinemaAndDate(movie, cinema, date);
        return ResponseEntity.ok(showTimes);
    }
}
