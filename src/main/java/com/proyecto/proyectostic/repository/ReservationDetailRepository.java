package com.proyecto.proyectostic.repository;

import com.proyecto.proyectostic.model.ReservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationDetailRepository extends JpaRepository<ReservationDetail, Integer> {
    List<ReservationDetail> findByReservationId(Integer reservationId);
}
