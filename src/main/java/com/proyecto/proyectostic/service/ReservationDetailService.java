package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.model.ReservationDetail;
import com.proyecto.proyectostic.repository.ReservationDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationDetailService {

    @Autowired
    private ReservationDetailRepository reservationDetailRepository;

    public List<ReservationDetail> getAllReservationDetails() {
        return reservationDetailRepository.findAll();
    }

    public ReservationDetail getReservationDetailById(Integer id) {
        return reservationDetailRepository.findById(id).orElse(null);
    }

    public ReservationDetail saveReservationDetail(ReservationDetail reservationDetail) {
        return reservationDetailRepository.save(reservationDetail);
    }

    public void deleteReservationDetail(Integer id) {
        reservationDetailRepository.deleteById(id);
    }
}

