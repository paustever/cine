package com.proyecto.proyectostic.repository;

import com.proyecto.proyectostic.model.Reservation;
import com.proyecto.proyectostic.model.ReservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
