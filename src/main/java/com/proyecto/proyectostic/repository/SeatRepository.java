package com.proyecto.proyectostic.repository;

import com.proyecto.proyectostic.model.Seat;
import com.proyecto.proyectostic.model.SeatId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, SeatId> {

    List<Seat> findByRoom_RoomId(Integer roomid);

    List<Seat> findByRoom_RoomIdAndAvailable(Integer roomId, Boolean available);
}


