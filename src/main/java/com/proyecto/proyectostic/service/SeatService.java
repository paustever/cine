package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.model.Seat;
import com.proyecto.proyectostic.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public Seat getSeatById(Integer seatId) throws Exception {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new Exception("Seat not found"));
    }

    public List<Seat> getSeatsByRoomId(Integer roomId) {
        return seatRepository.findByRoomRoomId(roomId);
    }

    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public void deleteSeat(Integer seatId) throws Exception {
        Seat seat = getSeatById(seatId);
        seatRepository.delete(seat);
    }

    public Seat updateSeatAvailability(Integer seatId, Boolean available) throws Exception {
        Seat seat = getSeatById(seatId);
        seat.setAvailable(available);
        return seatRepository.save(seat);
    }
}
