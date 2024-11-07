package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.excepcion.SeatNotAvailableException;
import com.proyecto.proyectostic.excepcion.SeatNotFoundException;
import com.proyecto.proyectostic.model.Seat;
import com.proyecto.proyectostic.model.SeatId;
import com.proyecto.proyectostic.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;
    public Seat getSeatById(SeatId seatId) throws Exception {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new Exception("Seat not found"));
    }






    public List<Seat> getSeatsByRoomId(Integer roomId) {
        return seatRepository.findByRoom_RoomId(roomId);
    }

    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    // Cambiar para aceptar SeatId en lugar de Integer
    public void deleteSeat(SeatId seatId) throws Exception {
        Seat seat = getSeatById(seatId);
        seatRepository.delete(seat);
    }

    // Cambiar para aceptar SeatId en lugar de Integer

}
