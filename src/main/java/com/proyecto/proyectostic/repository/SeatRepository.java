package com.proyecto.proyectostic.repository;

import com.proyecto.proyectostic.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

    List<Seat> findByRoomRoomId(Integer roomId);
}


