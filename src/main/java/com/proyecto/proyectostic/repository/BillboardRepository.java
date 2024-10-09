package com.proyecto.proyectostic.repository;

import com.proyecto.proyectostic.model.Billboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillboardRepository extends JpaRepository<Billboard, Integer> {
    Billboard findByCinemaCinemaId(Integer cinemaId);
}

