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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

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


        List<Seat> seats = seatRepository.findAllById(seatIds);
        if (seats.size() != seatIds.size()) {
            throw new SeatNotFoundException("Alguno de los asientos no se encontró");
        }

        List<Seat> reservedSeats = showtime.getReservedSeats();

        for (Seat seat : seats) {
            if (reservedSeats.contains(seat)) {
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
        List<ReservationDetail> reservationDetails = new ArrayList<>();
        for (Seat seat : seats) {
            reservedSeats.add(seat); // Marcar el asiento como reservado
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
            reservationDetails.add(detail);

        }
        savedReservation.setReservationDetails(reservationDetails);

        return savedReservation;
    }

        public void cancelReservation(String token, Integer reservationId) throws Exception {
            String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            User user = tokenService.getUserFromToken(actualToken)
                    .orElseThrow(() -> new InvalidCredentialsException("Invalid token provided"));

        Optional<Reservation> reservation = reservationRepository.findById(reservationId);

        if (!reservation.isPresent()) {
            throw new Exception("Reservation with Id: " + reservationId + " not found");
        }

        Reservation reservationFound = reservation.get();
        if (!reservationFound.getUser().equals(user)) {
            throw new InvalidCredentialsException("User does not have permission to cancel this reservation");
        }
        List<ReservationDetail> reservationDetails = reservationDetailRepository.findByReservationId(reservationId);

        reservationDetailRepository.deleteAll(reservationDetails);
        reservationRepository.delete(reservationFound);
    }

    public List<Reservation> getReservationsByUserId(Integer userId) {
        return reservationRepository.findByUser_UserId(userId);
    }

    }


