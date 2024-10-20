package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.model.Cinema;
import com.proyecto.proyectostic.model.Movie;
import com.proyecto.proyectostic.model.ShowTime;
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


}

