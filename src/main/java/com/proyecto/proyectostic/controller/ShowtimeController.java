package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.model.Cinema;
import com.proyecto.proyectostic.model.Movie;
import com.proyecto.proyectostic.model.ShowTime;
import com.proyecto.proyectostic.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;

    @GetMapping
    public List<ShowTime> getAllShowtimes() {
        return showtimeService.getAllShowtimes();
    }

    @GetMapping("/{id}")
    public ShowTime getShowtimeById(@PathVariable Integer id) {
        return showtimeService.getShowtimeById(id);
    }

    @PostMapping
    public ShowTime createShowtime(@RequestBody ShowTime showtime) {
        return showtimeService.saveShowtime(showtime);
    }


    @DeleteMapping("/{id}")
    public void deleteShowtime(@PathVariable Integer id) {
        showtimeService.deleteShowtime(id);
    }
    @GetMapping("/movie/{movieId}")
    public Map<Cinema, List<ShowTime>> getShowTimesByMovie(@PathVariable Integer movieId) {
        Movie movie = new Movie();
        movie.setMovieId(movieId);  // Crear un objeto Movie con el ID proporcionado
        return showtimeService.getShowTimesByMovie(movie);
    }
    @GetMapping("/movie/{movieId}/date/{date}")
    public Map<Cinema, List<ShowTime>> getShowTimesByMovieAndDate(
            @PathVariable Integer movieId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        Movie movie = new Movie();
        movie.setMovieId(movieId);

        // Cambiar la lógica para comparar solo las fechas (ignorando la hora)
        return showtimeService.getShowTimesByMovieAndDate(movie, date);
    }


}

