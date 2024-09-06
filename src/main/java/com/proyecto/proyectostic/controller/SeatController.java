package com.proyecto.proyectostic.controller;
import com.proyecto.proyectostic.excepcion.SeatAlreadyReservedException;
import com.proyecto.proyectostic.excepcion.SeatNotFoundException;
import com.proyecto.proyectostic.excepcion.SeatNotReservedException;
import com.proyecto.proyectostic.model.Seat;
import com.proyecto.proyectostic.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getAllSeats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Integer id) {
        return seatService.getSeatById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Seat createSeat(@RequestBody Seat seat) {
        return seatService.saveSeat(seat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Integer id) {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reserve")
    public ResponseEntity<Seat> reserveSeat(@PathVariable Integer id) {
        try {
            Seat reservedSeat = seatService.reserveSeat(id);
            return ResponseEntity.ok(reservedSeat);
        } catch (SeatNotFoundException | SeatAlreadyReservedException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Liberar un asiento
    @PutMapping("/{id}/release")
    public ResponseEntity<Seat> releaseSeat(@PathVariable Integer id) {
        try {
            Seat releasedSeat = seatService.releaseSeat(id);
            return ResponseEntity.ok(releasedSeat);
        } catch (SeatNotFoundException | SeatNotReservedException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
