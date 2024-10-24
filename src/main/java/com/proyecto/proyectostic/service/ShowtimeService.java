package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.model.Cinema;
import com.proyecto.proyectostic.model.Movie;
import com.proyecto.proyectostic.model.ShowTime;
import com.proyecto.proyectostic.model.ShowTimeId;
import com.proyecto.proyectostic.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    // Obtener todos los horarios de las funciones
    public List<ShowTime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    // Guardar un nuevo horario de función
    public ShowTime saveShowtime(ShowTime showtime) {
        return showtimeRepository.save(showtime);
    }

    // Obtener horarios de una película agrupados por cines
    public Map<Cinema, List<ShowTime>> getShowTimesByMovie(Movie movie) {
        List<ShowTime> showTimes = showtimeRepository.findByMovie(movie);

        // Agrupar los showtimes por cinema
        return showTimes.stream()
                .collect(Collectors.groupingBy(showTime -> showTime.getBillboard().getCinema()));
    }

    // Obtener fechas disponibles para una película en un cine específico
    public List<Date> getAvailableDatesByMovieAndCinema(Movie movie, Cinema cinema) {
        List<ShowTime> showTimes = showtimeRepository.findByMovieAndCinema(movie, cinema);

        // Filtrar por fechas disponibles y eliminar duplicados
        return showTimes.stream()
                .map(ShowTime::getShowtimeDate)
                .distinct() // Remover fechas duplicadas
                .collect(Collectors.toList());
    }

    // Obtener los horarios para una película en un cine en una fecha específica
    public List<ShowTime> getShowTimesByMovieCinemaAndDate(Movie movie, Cinema cinema, Date date) {
        return showtimeRepository.findByMovieAndCinemaAndShowtimeDate(movie, cinema, date)
                .stream()
                .sorted(Comparator.comparing(ShowTime::getShowtimeDate)) // Ordenar por horario
                .collect(Collectors.toList());
    }

    // Agrupar los horarios por cine y fecha
    public Map<Cinema, List<ShowTime>> getShowTimesByMovieCinemaAndDate(Movie movie, Date date) {
        List<ShowTime> showTimes = showtimeRepository.findByMovieAndShowtimeDate(movie, date);

        // Agrupar los showtimes por cine y ordenar por la hora de la función
        return showTimes.stream()
                .sorted(Comparator.comparing(ShowTime::getShowtimeDate)) // Ordenar por horario
                .collect(Collectors.groupingBy(showTime -> showTime.getBillboard().getCinema()));
    }

}

