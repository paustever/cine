package com.proyecto.proyectostic.service;

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
    @Transactional
    public void reserveSeats(User user, List<Integer> seatIds, ShowTime showtime) throws Exception {

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setShowtime(showtime);
        reservation.setDate(showtime.getShowtimeDate());

        for (Integer seatId : seatIds) {
            Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new Exception("Seat not found"));
            if (!seat.getAvailable()) {
                throw new Exception("Seat " + seat.getSeatNumber() + " is already taken");
            }
            seat.setAvailable(false);
            seatRepository.save(seat);

            ReservationDetail reservationDetail = new ReservationDetail();
            reservationDetail.setReservation(reservation);
            reservationDetail.setSeat(seat);
            reservationDetailRepository.save(reservationDetail);
        }
        reservationRepository.save(reservation);
    }
}


