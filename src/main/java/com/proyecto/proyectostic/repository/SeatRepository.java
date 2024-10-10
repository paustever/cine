package com.proyecto.proyectostic.repository;

import com.proyecto.proyectostic.model.Seat;
import com.proyecto.proyectostic.model.SeatId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, SeatId> {


    List<Seat> findByRoomRoomId(Integer roomId);

    List<Seat> findByRoomRoomIdAndAvailable(Integer roomId, Boolean available);
}
