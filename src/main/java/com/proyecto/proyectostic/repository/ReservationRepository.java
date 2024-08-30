package com.proyecto.proyectostic.repository;

import com.proyecto.proyectostic.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
