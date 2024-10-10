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




    public Seat reserveSeat(SeatId seatId) throws SeatNotAvailableException {
        Optional<Seat> optionalSeat = seatRepository.findById(seatId);

        if (optionalSeat.isEmpty()) {
            throw new SeatNotFoundException("Seat with ID " + seatId + " not found");
        }

            Seat seat = optionalSeat.get();

            if (!seat.getAvailable()) {
                throw new SeatNotAvailableException("Seat " + seat.getSeatNumber() + " is already taken");
            }

            seat.setAvailable(false);
            return seatRepository.save(seat);  // Actualiza el asiento y lo devuelve
        }



    public List<Seat> getSeatsByRoomId(Integer roomId) {
        return seatRepository.findByRoomRoomId(roomId);
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
    public Seat updateSeatAvailability(SeatId seatId, Boolean available) throws Exception {
        Seat seat = getSeatById(seatId);
        seat.setAvailable(available);
        return seatRepository.save(seat);
    }
}
