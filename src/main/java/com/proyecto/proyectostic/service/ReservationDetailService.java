package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.model.ReservationDetail;
import com.proyecto.proyectostic.repository.ReservationDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationDetailService {

    private final ReservationDetailRepository reservationDetailRepository;

    @Autowired
    public ReservationDetailService(ReservationDetailRepository reservationDetailRepository) {
        this.reservationDetailRepository = reservationDetailRepository;
    }

    public List<ReservationDetail> getAllReservationDetails() {
        return reservationDetailRepository.findAll();
    }

    public Optional<ReservationDetail> getReservationDetailById(Integer id) {
        return reservationDetailRepository.findById(id);
    }

    public ReservationDetail saveReservationDetail(ReservationDetail reservationDetail) {
        return reservationDetailRepository.save(reservationDetail);
    }

    public void deleteReservationDetail(Integer id) {
        reservationDetailRepository.deleteById(id);
    }
}
