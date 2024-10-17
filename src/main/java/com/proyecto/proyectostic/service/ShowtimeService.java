package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.excepcion.BillboardNotFoundException;
import com.proyecto.proyectostic.excepcion.ShowTimeNotFoundException;
import com.proyecto.proyectostic.model.*;
import com.proyecto.proyectostic.repository.BillboardRepository;
import com.proyecto.proyectostic.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private BillboardRepository billboardRepository;


    public List<ShowTime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    public ShowTime getShowtimeById(Integer id) {
        return showtimeRepository.findById(id).orElse(null);
    }

    public ShowTime saveShowtime(ShowTime showtime) {
        return showtimeRepository.save(showtime);
    }

    public void deleteShowtime(Integer id) {
        showtimeRepository.deleteById(id);
    }

    public Map<Cinema, List<ShowTime>> getShowTimesByMovie(Movie movie) {
        List<ShowTime> showTimes = showtimeRepository.findByMovie(movie);

        // Agrupar los showtimes por cinema
        return showTimes.stream()
                .collect(Collectors.groupingBy(showTime -> showTime.getBillboard().getCinema()));
    }

    public Map<Cinema, List<ShowTime>> getShowTimesByMovieAndDate(Movie movie, Date date) {
        List<ShowTime> showTimes = showtimeRepository.findByMovieAndShowtimeDate(movie, date);

        // Agrupar los showtimes por cine y ordenar por la hora de la función
        return showTimes.stream()
                .sorted(Comparator.comparing(ShowTime::getShowtimeDate)) // Ordenar por horario
                .collect(Collectors.groupingBy(showTime -> showTime.getBillboard().getCinema()));
    }


    public List<ShowTime> getShowtimesByMovieAndCinema(Integer movieId, Integer cinemaId) {
        Billboard billboard = billboardRepository.findByCinema_CinemaId(cinemaId);
        if (billboard == null) {
            throw new BillboardNotFoundException("No se encontró una cartelera para el cine con ID " + cinemaId);
        }
        List<ShowTime> showtimes = new ArrayList<>();
        for (ShowTime showTime : billboard.getShowTimes()) {
            if (showTime.getMovie().getMovieId().equals(movieId)) {
                showtimes.add(showTime);
            }
        }
        return showtimes;
    }
    public List<LocalDate> getAvailableDatesForMovieInCinema(Integer movieId, Integer cinemaId) {
        Billboard billboard = billboardRepository.findByCinema_CinemaId(cinemaId);
        if (billboard == null) {
            throw new BillboardNotFoundException("No se encontró una cartelera para el cine con ID " + cinemaId);
        }
        Set<LocalDate> availableDates = new HashSet<>();
        for (ShowTime showTime : billboard.getShowTimes()) {
            if (showTime.getMovie().getMovieId().equals(movieId)) {
                Date showtimeDate = showTime.getShowtimeDate();
                LocalDate localDate = Instant.ofEpochMilli(showtimeDate.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                availableDates.add(localDate);
            }
        }
        return new ArrayList<>(availableDates);
    }

    public ShowTime getShowtimeByMovieCinemaDateAndTime(Integer movieId, Integer cinemaId, LocalDate date, LocalTime time) {
        Billboard billboard = billboardRepository.findByCinema_CinemaId(cinemaId);
        if (billboard == null) {
            throw new BillboardNotFoundException("No se encontró una cartelera para el cine con ID " + cinemaId);
        }
        for (ShowTime showTime : billboard.getShowTimes()) {
            if (showTime.getMovie().getMovieId().equals(movieId)) {
                // Convertir la fecha de ShowTime para comparar
                Date showtimeDate = showTime.getShowtimeDate();
                LocalDate localDate = Instant.ofEpochMilli(showtimeDate.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                LocalTime localTime = Instant.ofEpochMilli(showtimeDate.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalTime();
                // Verificar si coincide la fecha y el tiempo
                if (localDate.equals(date) && localTime.equals(time)) {
                    return showTime;
                }
            }
        }
        throw new ShowTimeNotFoundException("No se encontró un showtime para la película en el cine, fecha y hora especificados.");
    }
    public List<Seat> getAvailableSeatsForShowtime(Integer showtimeId) {
        ShowTime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new ShowTimeNotFoundException("No se encontró el showtime con ID " + showtimeId));

        // Obtener los asientos desde el Map y filtrarlos
        return showtime.getRoom().getSeats().values().stream() // Obtener los valores del Map (asientos)
                .filter(Seat::getAvailable) // Filtrar los asientos que están disponibles
                .collect(Collectors.toList()); // Convertir la lista filtrada a List
    }
}

