package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.model.ShowTime;
import com.proyecto.proyectostic.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;

    @GetMapping
    public List<ShowTime> getAllShowtimes() {
        return showtimeService.getAllShowtimes();
    }

    @GetMapping("/{id}")
    public ShowTime getShowtimeById(@PathVariable Integer id) {
        return showtimeService.getShowtimeById(id);
    }

    @PostMapping
    public ShowTime createShowtime(@RequestBody ShowTime showtime) {
        return showtimeService.saveShowtime(showtime);
    }

    @DeleteMapping("/{id}")
    public void deleteShowtime(@PathVariable Integer id) {
        showtimeService.deleteShowtime(id);
    }
}

