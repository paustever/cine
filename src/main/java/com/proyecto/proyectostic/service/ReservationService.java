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


    public void reserveSeats(User user, List<SeatId> seatIds, ShowTime showtime) throws SeatNotAvailableException {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setShowtime(showtime);
        reservation.setDate(showtime.getShowtime_date());

        for (SeatId seatId : seatIds) {
            // Delegar la lógica de reserva del asiento a SeatService
            Seat seat = seatService.reserveSeat(seatId);

            // Crear el detalle de la reserva
            ReservationDetail reservationDetail = new ReservationDetail();
            reservationDetail.setReservation(reservation);
            reservationDetail.setSeat(seat);
            reservationDetailRepository.save(reservationDetail);
        }
        user.getReservations().add(reservation);

        reservationRepository.save(reservation);
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

        // elimino la reserva de la lista del usuario correspondiente
        User user = reservationfound.getUser();
        user.getReservations().remove(reservationfound);

        // Eliminar la reserva de la base de datos(reservationFound);
    }


}


