package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.model.Billboard;
import com.proyecto.proyectostic.model.Movie;
import com.proyecto.proyectostic.service.BillboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/billboards")
public class BillboardController {

    private final BillboardService billboardService;

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

    // Agregar una película a la cartelera de un cine
    @PostMapping("/{cinemaId}/add-movie/{movieId}")
    public ResponseEntity<Billboard> addMovieToBillboard(@PathVariable Integer cinemaId, @PathVariable Integer movieId) {
        Billboard updatedBillboard = billboardService.addMovieToBillboard(cinemaId, movieId);
        return ResponseEntity.ok(updatedBillboard);
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

}
