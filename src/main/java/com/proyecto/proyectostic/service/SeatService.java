package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.excepcion.SeatNotAvailableException;
import com.proyecto.proyectostic.excepcion.SeatNotFoundException;
import com.proyecto.proyectostic.model.Seat;
import com.proyecto.proyectostic.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public Optional<Seat> getSeatById(Integer id) {
        return seatRepository.findById(id);
    }

    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public void deleteSeat(Integer id) {
        seatRepository.deleteById(id);
    }

    public Seat reserveSeat(Integer seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException("Seat with ID " + seatId + " not found"));
        if (!seat.isAvailable()) {
            throw new SeatNotAvailableException("Seat with ID " + seatId + " is not available");
        }
        seat.setAvailable(false);
        return seatRepository.save(seat);     }


    public Seat releaseSeat(Integer seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException("Seat with ID " + seatId + " not found"));

        seat.setAvailable(true);
        return seatRepository.save(seat);
    }
}
