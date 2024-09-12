package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.model.*;
import com.proyecto.proyectostic.repository.ReservationDetailRepository;
import com.proyecto.proyectostic.repository.ReservationRepository;
import com.proyecto.proyectostic.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ReservationDetailRepository reservationDetailRepository;


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
    public void reserveSeats(User user, List<Integer> seatIds, ShowTime showtime) throws Exception {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setShowtime(showtime);
        reservation.setDate(showtime.getShowtimeDate());
        reservationRepository.save(reservation);

        for (Integer seatId : seatIds) {
            Seat seat = SeatRepository.findById(seatId).orElseThrow(() -> new Exception("Seat not found"));
            if (!seat.getIsAvailable()) {
                throw new Exception("Seat " + seat.getSeatNumber() + " is already taken");
            }
            seat.setIsAvailable(false); // Marcar como reservado
            seatRepository.save(seat);

            ReservationDetail reservationDetail = new ReservationDetail();
            reservationDetail.setReservation(reservation);
            reservationDetail.setSeat(seat);
            reservationDetailRepository.save(reservationDetail);
        }
    }
}


