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
            Optional<User> userFromToken = tokenService.getUserFromToken(actualToken);
            User user = userFromToken.orElseThrow(() -> new InvalidCredentialsException("Invalid token"));

            // Paso 2: Verificar disponibilidad de los asientos en el contexto del ShowTime
            for (SeatId seatId : seatIds) {
                Optional<Seat> seatOptional = seatRepository.findById(seatId);
                if (!seatOptional.isPresent()) {
                    throw new SeatNotFoundException("Seat with ID: " + seatId + " not found");
                }
                Seat seat = seatOptional.get();

                // Verificar si el asiento está reservado para este showtime
                boolean isSeatTaken = reservationDetailRepository.existsBySeatIdAndShowtime(seatId, showtime);
                if (isSeatTaken) {
                    throw new SeatNotAvailableException("Seat with ID: " + seatId + " is already taken for this showtime");
                }
            }

            // Paso 3: Crear la reserva
            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setShowtime(showtime);
            reservation.setDate(new Date()); // fecha actual

            Reservation savedReservation = reservationRepository.save(reservation);

            // Paso 4: Actualizar los asientos y crear detalles de la reserva
            for (SeatId seatId : seatIds) {
                Seat seat = seatRepository.findById(seatId).get();
                seat.setAvailable(false);  // Marcar el asiento como no disponible solo para este showtime

                // Crear los detalles de la reserva
                ReservationDetail detail = new ReservationDetail();
                detail.setReservation(savedReservation);
                detail.setSeat(seat);
                detail.setShowtime(showtime);  // Asociar el showtime
                reservationDetailRepository.save(detail);
            }

            return savedReservation;
    }



    public void cancelReservation(Integer reservationId) throws Exception {
        Optional reservation = reservationRepository.findById(reservationId);

        if (!reservation.isPresent()){
            throw new Exception("reservation with Id: "+ reservationId + " not found" );
        }

        Reservation reservationfound= (Reservation) reservation.get();
        List<ReservationDetail> reservationDetails = reservationDetailRepository.findByReservationId(reservationId);

        for (ReservationDetail detail : reservationDetails) {
            Seat seat = detail.getSeat();
            seat.setAvailable(true);
            seatRepository.save(seat);
        }
        reservationDetailRepository.deleteAll(reservationDetails);
        reservationRepository.delete(reservationfound);
    }
    public List<Reservation> getReservationsByUserId(Integer userId) {
        return reservationRepository.findByUser_UserId(userId);
    }
}


