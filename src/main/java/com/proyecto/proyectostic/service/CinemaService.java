package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.excepcion.CinemaAlreadyExistsException;
import com.proyecto.proyectostic.model.Cinema;
import com.proyecto.proyectostic.model.User;
import com.proyecto.proyectostic.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaService {

    private final CinemaRepository cinemaRepository;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    public Optional<Cinema> getCinemaById(Integer id) {
        return cinemaRepository.findById(id);
    }

    public Cinema saveCinema(Cinema cinema) {
        return cinemaRepository.save(cinema);
    }

    public void deleteCinema(Integer id) {
        cinemaRepository.deleteById(id);
    }

    public Cinema registerCinema(Cinema cinema) {
        if (cinemaRepository.existsById(cinema.getCinemaId())) {
            throw new CinemaAlreadyExistsException("Cinema with ID " + cinema.getCinemaId() + " already exists");
        }
        return cinemaRepository.save(cinema);
    }
}
