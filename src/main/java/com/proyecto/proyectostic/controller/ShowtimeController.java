package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.model.Cinema;
import com.proyecto.proyectostic.model.Movie;
import com.proyecto.proyectostic.model.Seat;
import com.proyecto.proyectostic.model.ShowTime;
import com.proyecto.proyectostic.service.CinemaService;
import com.proyecto.proyectostic.service.MovieService;
import com.proyecto.proyectostic.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaService cinemaService;

    // Obtener todos los horarios
    @GetMapping
    public List<ShowTime> getAllShowtimes() {
        return showtimeService.getAllShowtimes();
    }

    // Crear un nuevo horario
    @PostMapping
    public ShowTime createShowtime(@RequestBody ShowTime showtime) {
        return showtimeService.saveShowtime(showtime);
    }

    // Obtener horarios de una película agrupados por cines
    @GetMapping("/by-movie/{movieId}")
    public ResponseEntity<Map<Cinema, List<ShowTime>>> getShowTimesByMovie(@PathVariable Integer movieId) {
        Movie movie = movieService.findById(movieId);
        return ResponseEntity.ok(showtimeService.getShowTimesByMovie(movie));
    }

    // Obtener fechas disponibles para una película en un cine específico
    @GetMapping("/by-movie-cinema/{movieId}/{cinemaId}")
    public ResponseEntity<List<Date>> getAvailableDatesByMovieAndCinema(@PathVariable Integer movieId, @PathVariable Integer cinemaId) {
        Movie movie = movieService.findById(movieId);
        Cinema cinema = cinemaService.findById(cinemaId);
        return ResponseEntity.ok(showtimeService.getAvailableDatesByMovieAndCinema(movie, cinema));
    }

    // Obtener horarios de una película en un cine y una fecha específica
    @GetMapping("/by-movie-cinema-date/{movieId}/{cinemaId}/{date}")
    public ResponseEntity<List<ShowTime>> getShowTimesByMovieCinemaAndDate(@PathVariable Integer movieId,
                                                                           @PathVariable Integer cinemaId,
                                                                           @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        Movie movie = movieService.findById(movieId);
        Cinema cinema = cinemaService.findById(cinemaId);
        return ResponseEntity.ok(showtimeService.getShowTimesByMovieCinemaAndDate(movie, cinema, date));
    }
}
