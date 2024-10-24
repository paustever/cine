package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.excepcion.BillboardNotFoundException;
import com.proyecto.proyectostic.excepcion.CinemaNotFoundException;
import com.proyecto.proyectostic.excepcion.MovieNotFoundException;
import com.proyecto.proyectostic.model.*;
import com.proyecto.proyectostic.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ShowtimeRepository showTimeRepository;

    // Obtener todas las películas de una cartelera
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

    // Agregar una película a una cartelera
    public ShowTime addMovieToBillboard(Integer billboardId, Integer movieId, Integer roomId, Date showtimeDate) {
        Billboard billboard = billboardRepository.findById(billboardId)
                .orElseThrow(() -> new EntityNotFoundException("Billboard not found"));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        ShowTime showTime = new ShowTime();
        showTime.setBillboard(billboard);
        showTime.setMovie(movie);
        showTime.setRoom(room);
        showTime.setShowtimeDate(showtimeDate);

        // Guardar el nuevo ShowTime
        showTimeRepository.save(showTime);

        return showTime;
    }

    // Eliminar una película de la cartelera de un cine
    public Billboard removeMovieFromBillboard(Integer cinemaId, Integer movieId) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new CinemaNotFoundException("Cine con ID " + cinemaId + " no encontrado"));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Película con ID " + movieId + " no encontrada"));

        Billboard billboard = billboardRepository.findByCinema_CinemaId(cinemaId);
        if (billboard == null) {
            throw new BillboardNotFoundException("No hay una cartelera asociada al cine");
        }

        boolean removed = billboard.getShowTimes().removeIf(showTime -> showTime.getMovie().equals(movie));
        if (!removed) {
            throw new BillboardNotFoundException("La película '" + movie.getName() + "' no está en la cartelera del cine");
        }

        return billboardRepository.save(billboard);
    }

    // Obtener una cartelera por ID
    public Optional<Billboard> getBillboardById(Integer billboardId) {
        return billboardRepository.findById(billboardId);
    }

    // Obtener todas las carteleras
    public List<Billboard> getAllBillboards() {
        return billboardRepository.findAll();
    }

    // Guardar una nueva cartelera
    public Billboard saveBillboard(Billboard billboard) {
        return billboardRepository.save(billboard);
    }

    // Eliminar una cartelera por ID
    public void deleteBillboard(Integer id) {
        billboardRepository.deleteById(id);
    }

    // Obtener todas las películas de todas las carteleras
    public List<Movie> getAllMoviesFromAllBillboards() {
        List<Billboard> billboards = billboardRepository.findAll();
        List<Movie> movies = new ArrayList<>();

        for (Billboard billboard : billboards) {
            for (ShowTime showTime : billboard.getShowTimes()) {
                Movie movie = showTime.getMovie();
                if (!movies.contains(movie)) {
                    movies.add(movie);
                }
            }
        }

        return movies;
    }

    // Obtener las carteleras con horarios disponibles a partir de la fecha actual
    public List<Billboard> getAvailableBillboards() {
        Date currentDate = new Date(); // Fecha actual
        return billboardRepository.findAvailableBillboards(currentDate);
    }
}
