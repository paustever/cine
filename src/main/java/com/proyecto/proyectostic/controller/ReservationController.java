package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.excepcion.SeatNotAvailableException;
import com.proyecto.proyectostic.excepcion.SeatNotFoundException;
import com.proyecto.proyectostic.model.Reservation;
import com.proyecto.proyectostic.model.SeatId;
import com.proyecto.proyectostic.model.ShowTime;
import com.proyecto.proyectostic.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Integer id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.saveReservation(reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Integer id) {
        reservationService.deleteReservation(id);
    }

    @DeleteMapping("/cancel/{reservationId}")
    public ResponseEntity<?> cancelReservation(@PathVariable Integer reservationId) {
        try {
            reservationService.cancelReservation(reservationId);
            return ResponseEntity.ok("Reservation cancelled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    @PostMapping("/reserve")
    public ResponseEntity<?> reserveSeats(@RequestHeader("Authorization") String token,
                                          @RequestBody List<SeatId> seatIds,
                                          @RequestBody ShowTime showtime) {
        try {
            Reservation reservation = reservationService.reserveSeats(token, seatIds, showtime);
            return ResponseEntity.ok(reservation);
        } catch (SeatNotAvailableException | SeatNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());  // Error por asientos no disponibles o no encontrados
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while processing the reservation.");
        }
    }
}


