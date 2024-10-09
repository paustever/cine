package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.excepcion.BillboardAlreadyExistsException;
import com.proyecto.proyectostic.excepcion.BillboardNotFoundException;
import com.proyecto.proyectostic.excepcion.CinemaNotFoundException;
import com.proyecto.proyectostic.excepcion.MovieNotFoundException;
import com.proyecto.proyectostic.model.Billboard;
import com.proyecto.proyectostic.model.Cinema;
import com.proyecto.proyectostic.model.Movie;
import com.proyecto.proyectostic.model.ShowTime;
import com.proyecto.proyectostic.repository.BillboardRepository;
import com.proyecto.proyectostic.repository.CinemaRepository;
import com.proyecto.proyectostic.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BillboardService {

    @Autowired
    private BillboardRepository billboardRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getMoviesFromBillboard(Integer billboardId) {
        Billboard billboard = billboardRepository.findById(billboardId)
                .orElseThrow(() -> new BillboardNotFoundException("Cartelera con ID " + billboardId + " no encontrada"));

        List<ShowTime> showTimes = billboard.getShowTimes();
        List<Movie> movies = new ArrayList<>();

        for (ShowTime showTime : showTimes) {
            Movie movie = showTime.getMovie();
            if (!movies.contains(movie)) {
                movies.add(movie);
            }
        }

        return movies;
    }

    public Billboard addMovieToBillboard(Integer cinemaId, Integer movieId) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new CinemaNotFoundException("Cine con ID " + cinemaId + " no encontrado"));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Película con ID " + movieId + " no encontrada"));

        Billboard billboard = billboardRepository.findByCinemaCinemaId(cinemaId);

        if (billboard == null) {
            billboard = new Billboard();
            billboard.setCinema(cinema);
        }

        if (billboard.getShowTimes().stream().anyMatch(showTime -> showTime.getMovie().equals(movie))) {
            throw new BillboardAlreadyExistsException("La película '" + movie.getName() + "' ya está en la cartelera del cine ");
        }

        // Agregar ShowTime en lugar de agregar directamente a movies
        ShowTime showTime = new ShowTime();
        showTime.setMovie(movie);
        showTime.setBillboard(billboard); // Asegúrate de que ShowTime tenga un campo para Billboard
        billboard.getShowTimes().add(showTime);

        return billboardRepository.save(billboard);
    }

    public Billboard removeMovieFromBillboard(Integer cinemaId, Integer movieId) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new CinemaNotFoundException("Cine con ID " + cinemaId + " no encontrado"));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Película con ID " + movieId + " no encontrada"));

        Billboard billboard = billboardRepository.findByCinemaCinemaId(cinemaId);
        if (billboard == null) {
            throw new BillboardNotFoundException("No hay una cartelera asociada al cine ");
        }

        boolean removed = billboard.getShowTimes().removeIf(showTime -> showTime.getMovie().equals(movie));
        if (!removed) {
            throw new BillboardNotFoundException("La película '" + movie.getName() + "' no está en la cartelera del cine ");
        }

        return billboardRepository.save(billboard);
    }

    public Billboard getBillboardByCinema(Integer cinemaId) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new CinemaNotFoundException("Cine con ID " + cinemaId + " no encontrado"));

        Billboard billboard = billboardRepository.findByCinemaCinemaId(cinemaId);
        if (billboard == null) {
            throw new BillboardNotFoundException("No hay una cartelera asociada al cine ");
        }

        return billboard;
    }
    public Optional<Billboard> getBillboardById(Integer billboardId) {
        return billboardRepository.findById(billboardId);
    }

    public List<Billboard> getAllBillboards() {
        return billboardRepository.findAll();
    }

    public Billboard saveBillboard(Billboard billboard) {
        return billboardRepository.save(billboard);
    }

    public void deleteBillboard(Integer id) {
        billboardRepository.deleteById(id);
    }
}
