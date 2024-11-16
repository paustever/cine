package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.excepcion.InvalidCredentialsException;
import com.proyecto.proyectostic.excepcion.SeatNotAvailableException;
import com.proyecto.proyectostic.excepcion.SeatNotFoundException;
import com.proyecto.proyectostic.model.Reservation;
import com.proyecto.proyectostic.model.SeatId;
import com.proyecto.proyectostic.model.ShowTime;
import com.proyecto.proyectostic.repository.ShowtimeRepository;
import com.proyecto.proyectostic.service.ReservationService;
import com.proyecto.proyectostic.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ShowtimeService showtimeService;
    @Autowired
    private ShowtimeRepository showtimeRepository;

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveSeats(@RequestHeader("Authorization") String token,
                                          @RequestBody List<SeatId> seatIds,
                                          @RequestParam Integer movieId,
                                          @RequestParam Integer roomId,
                                          @RequestParam String showtimeDate) {
        try {
            // Convertir el String showtimeDate a un Date con el mismo formato
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            dateFormat.setLenient(false); // Asegura que solo acepte fechas estrictamente válidas
            Date parsedShowtimeDate = dateFormat.parse(showtimeDate);

            // Obtener el ShowTime usando los identificadores proporcionados
            Optional<ShowTime> showtimeOpt = showtimeRepository.findByMovieRoomAndDate(movieId, roomId, parsedShowtimeDate);
            if (showtimeOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Showtime not found");
            }
            ShowTime showtime = showtimeOpt.get();

            Reservation reservation = reservationService.reserveSeats(token, seatIds, showtime);
            return ResponseEntity.ok(reservation);
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format. Use 'yyyy-MM-dd'T'HH:mm:ss'");
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (SeatNotAvailableException | SeatNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Integer id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
        }
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable Integer userId) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/cancel/{reservationId}")
    public ResponseEntity<?> cancelReservation(@RequestHeader("Authorization") String token, @PathVariable Integer reservationId) {
        try {
            reservationService.cancelReservation(token, reservationId);
            return ResponseEntity.ok("Reservation cancelled successfully");
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
