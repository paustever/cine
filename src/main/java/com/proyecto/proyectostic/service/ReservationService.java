package com.proyecto.proyectostic.service;

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


    @Transactional  // Añadir transacción para asegurar atomicidad
    public void reserveSeats(User user, List<SeatId> seatIds, ShowTime showtime) throws Exception {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setShowtime(showtime);
        reservation.setDate(showtime.getShowtimeDate());

        // Verifica que todos los asientos estén disponibles antes de continuar
        List<Seat> seatsToReserve = new ArrayList<>();
        for (SeatId seatId : seatIds) {
            Seat seat = seatService.getSeatById(seatId); // Obtener el asiento
            if (!seat.getAvailable()) {
                throw new SeatNotAvailableException("El asiento " + seatId + " ya está ocupado");
            }
            seatsToReserve.add(seat); // Agregar a la lista de asientos a reservar
        }

        // Guardar la reserva
        reservationRepository.save(reservation);

        // Luego de verificar, realizar las reservas de los asientos
        for (Seat seat : seatsToReserve) {
            seat.setAvailable(false);  // Marcar como reservado
            seatService.saveSeat(seat);  // Guardar el estado actualizado

            // Crear y guardar el detalle de la reserva
            ReservationDetail reservationDetail = new ReservationDetail();
            reservationDetail.setReservation(reservation);
            reservationDetail.setSeat(seat);
            reservationDetailRepository.save(reservationDetail);
        }
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


