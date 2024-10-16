package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.model.Billboard;
import com.proyecto.proyectostic.model.Cinema;
import com.proyecto.proyectostic.model.Movie;
import com.proyecto.proyectostic.model.ShowTime;
import com.proyecto.proyectostic.service.BillboardService;
import com.proyecto.proyectostic.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/billboards")
public class BillboardController {

    private final BillboardService billboardService;
    @Autowired
    private ShowtimeService showtimeService;


    @Autowired
    public BillboardController(BillboardService billboardService) {
        this.billboardService = billboardService;
    }

    // Obtener todas las carteleras
    @GetMapping
    public List<Billboard> getAllBillboards() {
        return billboardService.getAllBillboards();
    }

    // Obtener una cartelera por ID
    @GetMapping("/{id}")
    public ResponseEntity<Billboard> getBillboardById(@PathVariable Integer id) {
        Optional<Billboard> optionalBillboard = billboardService.getBillboardById(id);
        return optionalBillboard.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener todas las películas de todas las carteleras
    @GetMapping("/movies")
    public List<Movie> getAllMovies() {
        return billboardService.getAllMoviesFromAllBillboards();
    }

    @PostMapping("/{billboardId}/add-movie/{movieId}/{roomId}")
    public ResponseEntity<ShowTime> addMovieToBillboard(
            @PathVariable Integer billboardId,
            @PathVariable Integer movieId,
            @PathVariable Integer roomId,
            @RequestParam Date showtimeDate) {

        ShowTime showTime = billboardService.addMovieToBillboard(billboardId, movieId, roomId, showtimeDate);
        return ResponseEntity.ok(showTime);
    }
    @GetMapping("/{billboardId}/movies")
    public ResponseEntity<List<Movie>> getMoviesFromBillboard(@PathVariable Integer billboardId) {
        List<Movie> movies = billboardService.getMoviesFromBillboard(billboardId);
        return ResponseEntity.ok(movies);
    }

    // Eliminar una película de la cartelera de un cine
    @DeleteMapping("/{cinemaId}/remove-movie/{movieId}")
    public ResponseEntity<Billboard> removeMovieFromBillboard(@PathVariable Integer cinemaId, @PathVariable Integer movieId) {
        Billboard updatedBillboard = billboardService.removeMovieFromBillboard(cinemaId, movieId);
        return ResponseEntity.ok(updatedBillboard);
    }

    // Crear una nueva cartelera
    @PostMapping
    public Billboard createBillboard(@RequestBody Billboard billboard) {
        return billboardService.saveBillboard(billboard);
    }

    // Eliminar una cartelera por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBillboard(@PathVariable Integer id) {
        billboardService.deleteBillboard(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para obtener las carteleras disponibles con horarios a partir de hoy
    @GetMapping("/available")
    public ResponseEntity<List<Billboard>> getAvailableBillboards() {
        List<Billboard> availableBillboards = billboardService.getAvailableBillboards();
        return ResponseEntity.ok(availableBillboards);
    }


    @GetMapping("/movies/{movieId}/cinemas")
    public List<Cinema> getCinemasByMovie(@PathVariable Integer movieId) {
        return billboardService.getCinemasByMovie(movieId);
    }

    @GetMapping("/movies/{movieId}/cinemas/{cinemaId}/showtimes")
    public List<ShowTime> getShowtimesByMovieAndCinema(@PathVariable Integer movieId, @PathVariable Integer cinemaId) {
        return showtimeService.getShowtimesByMovieAndCinema(movieId, cinemaId);
    }


}
