package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.model.ShowTime;
import com.proyecto.proyectostic.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

