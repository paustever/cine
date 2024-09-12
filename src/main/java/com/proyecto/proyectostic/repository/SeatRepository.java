package com.proyecto.proyectostic.repository;

import com.proyecto.proyectostic.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

    List<Seat> findByRoomRoomId(Integer roomId);

    List<Seat> findByRoomRoomIdAndIsAvailable(Integer roomId, Boolean isAvailable);

    Optional<Seat> findById(Integer seatId);
}



