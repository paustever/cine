package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.model.Seat;
import com.proyecto.proyectostic.model.SeatId;
import com.proyecto.proyectostic.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    // Ajustar para aceptar roomId, rowNumber, seatNumber
    @GetMapping("/{roomId}/{rowNumber}/{seatNumber}")
    public ResponseEntity<Seat> getSeatById(
            @PathVariable Integer roomId,
            @PathVariable Integer rowNumber,
            @PathVariable Integer seatNumber) {
        try {
            SeatId seatId = new SeatId(roomId, rowNumber, seatNumber);  // Crear SeatId
            Seat seat = seatService.getSeatById(seatId);
            return ResponseEntity.ok(seat);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/room/{roomId}")
    public List<Seat> getSeatsByRoomId(@PathVariable Integer roomId) {
        return seatService.getSeatsByRoomId(roomId);
    }

    @PostMapping
    public Seat createSeat(@RequestBody Seat seat) {
        return seatService.saveSeat(seat);
    }

    // Ajustar para aceptar roomId, rowNumber, seatNumber
    @DeleteMapping("/{roomId}/{rowNumber}/{seatNumber}")
    public ResponseEntity<Void> deleteSeat(
            @PathVariable Integer roomId,
            @PathVariable Integer rowNumber,
            @PathVariable Integer seatNumber) {
        try {
            SeatId seatId = new SeatId(roomId, rowNumber, seatNumber);  // Crear SeatId
            seatService.deleteSeat(seatId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Ajustar para aceptar roomId, rowNumber, seatNumber
    @PutMapping("/{roomId}/{rowNumber}/{seatNumber}/availability")
    public ResponseEntity<Seat> updateSeatAvailability(
            @PathVariable Integer roomId,
            @PathVariable Integer rowNumber,
            @PathVariable Integer seatNumber,
            @RequestParam Boolean available) {
        try {
            SeatId seatId = new SeatId(roomId, rowNumber, seatNumber);  // Crear SeatId
            Seat updatedSeat = seatService.updateSeatAvailability(seatId, available);
            return ResponseEntity.ok(updatedSeat);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
