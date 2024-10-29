package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.excepcion.InvalidCredentialsException;
import com.proyecto.proyectostic.excepcion.SeatNotAvailableException;
import com.proyecto.proyectostic.excepcion.SeatNotFoundException;
import com.proyecto.proyectostic.model.*;
import com.proyecto.proyectostic.repository.ReservationDetailRepository;
import com.proyecto.proyectostic.repository.ReservationRepository;
import com.proyecto.proyectostic.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ReservationDetailRepository reservationDetailRepository;
    @Autowired
    private SeatService seatService;  // Inyectar SeatService
    @Autowired
    private TokenService tokenService;


    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }



    @Transactional
    public Reservation reserveSeats(String token, List<SeatId> seatIds, ShowTime showtime) throws SeatNotAvailableException, SeatNotFoundException, InvalidCredentialsException {
        // Paso 1: Verificar si el usuario está autenticado
        String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        User user = tokenService.getUserFromToken(actualToken)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid token provided"));

        // Paso 2: Verificar la disponibilidad de todos los asientos
        List<Seat> seats = seatRepository.findAllById(seatIds);
        if (seats.size() != seatIds.size()) {
            throw new SeatNotFoundException("One or more seats not found");
        }

        for (Seat seat : seats) {
            if (seat.getAvailable() == null || !seat.getAvailable()) {
                throw new SeatNotAvailableException("Seat with Room ID " + seat.getRoomId() + ", Row Number " + seat.getRowNumber() + ", and Seat Number " + seat.getSeatNumber() + " is not available");
            }
        }

        // Paso 3: Crear la reserva
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setShowtime(showtime);
        reservation.setDate(new Date()); // fecha actual
        Reservation savedReservation = reservationRepository.save(reservation);

        // Paso 4: Actualizar los asientos y crear detalles de la reserva
        for (Seat seat : seats) {
            seat.setAvailable(false); // Marcar el asiento como no disponible
            seatRepository.save(seat);

            // Crear los detalles de la reserva usando ReservationDetailId
            ReservationDetailId detailId = new ReservationDetailId(savedReservation.getReservationId(), seat.getRoomId());
            ReservationDetail detail = new ReservationDetail();
            detail.setReservationId(savedReservation.getReservationId());
            detail.setSeatId(seat.getSeatNumber());
            detail.setReservation(savedReservation);
            detail.setSeat(seat);
            detail.setShowtime(showtime); // Asociar el showtime
            reservationDetailRepository.save(detail);
        }

        return savedReservation;
    }

        public void cancelReservation(Integer reservationId) throws Exception {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);

        if (!reservation.isPresent()) {
            throw new Exception("Reservation with Id: " + reservationId + " not found");
        }

        Reservation reservationFound = reservation.get();
        List<ReservationDetail> reservationDetails = reservationDetailRepository.findByReservationId(reservationId);

        for (ReservationDetail detail : reservationDetails) {
            Seat seat = detail.getSeat();
            seat.setAvailable(true);
            seatRepository.save(seat);
        }
        reservationDetailRepository.deleteAll(reservationDetails);
        reservationRepository.delete(reservationFound);
    }

    public List<Reservation> getReservationsByUserId(Integer userId) {
        return reservationRepository.findByUser_UserId(userId);
    }

    }


