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



    public Billboard removeMovieFromBillboard(Integer cinemaId, Integer movieId) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new CinemaNotFoundException("Cine con ID " + cinemaId + " no encontrado"));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Película con ID " + movieId + " no encontrada"));

        Billboard billboard = billboardRepository.findByCinema_CinemaId(cinemaId);
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

        Billboard billboard = billboardRepository.findByCinema_CinemaId(cinemaId);
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
    public List<Movie> getAllMoviesFromAllBillboards() {
        List<Billboard> billboards = billboardRepository.findAll();
        List<Movie> movies = new ArrayList<>();
        // Log para ver si se están obteniendo carteleras
        System.out.println("Carteleras encontradas: " + billboards.size());

        for (Billboard billboard : billboards) {
            for (ShowTime showTime : billboard.getShowTimes()) {
                Movie movie = showTime.getMovie();
                if (!movies.contains(movie)) {
                    movies.add(movie);
                }
            }
        }
        // Log para ver cuántas películas se encontraron
        System.out.println("Películas encontradas: " + movies.size());

        return movies;
    }


    // Metodo para obtener las carteleras con horarios disponibles a partir de la fecha actual
    public List<Billboard> getAvailableBillboards() {
        Date currentDate = new Date(); // Fecha actual
        return billboardRepository.findAvailableBillboards(currentDate);
    }

}
