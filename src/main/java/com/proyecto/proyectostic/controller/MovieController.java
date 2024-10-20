package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.model.Movie;
import com.proyecto.proyectostic.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {


    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Integer id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        String imagePath = "images/" + image.getOriginalFilename(); // Ruta donde se guardará la imagen
        File dest = new File(imagePath);
        image.transferTo(dest); // Guardar el archivo en el sistema

        return imagePath; // Esto se puede usar luego como coverImageUrl

    }
}
