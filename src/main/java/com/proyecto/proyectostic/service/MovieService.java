package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.excepcion.MovieAlreadyExistsException;
import com.proyecto.proyectostic.model.Movie;
import com.proyecto.proyectostic.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    public Optional<Movie> getMovieById(Integer id) {
        return movieRepository.findById(id);
    }
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }
    public void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
    }
    public Movie addMovie(Movie movie) {
        if (movieRepository.existsByName(movie.getName())) {
            throw new MovieAlreadyExistsException("Movie with name " + movie.getName() + " already exists");
        }
        return movieRepository.save(movie);
    }
    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }
}
