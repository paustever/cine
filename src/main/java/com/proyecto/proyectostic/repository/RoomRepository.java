package com.proyecto.proyectostic.repository;

import com.proyecto.proyectostic.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Room findByRoomId(Integer roomId);
}
