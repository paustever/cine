package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.model.Seat;
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

    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Integer id) {
        try {
            Seat seat = seatService.getSeatById(id);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Integer id) {
        try {
            seatService.deleteSeat(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}/availability")
    public ResponseEntity<Seat> updateSeatAvailability(@PathVariable Integer id, @RequestParam Boolean available) {
        try {
            Seat updatedSeat = seatService.updateSeatAvailability(id, available);
            return ResponseEntity.ok(updatedSeat);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

