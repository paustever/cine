package com.proyecto.proyectostic.repository;

import com.proyecto.proyectostic.model.Billboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface BillboardRepository extends JpaRepository<Billboard, Integer> {
    Billboard findByCinema_Cinemaid(Integer cinemaid);

    // Metodo para obtener todas las carteleras con horarios disponibles a partir de hoy
    @Query("SELECT b FROM Billboard b JOIN b.showTimes s WHERE s.showtime_date >= :currentDate")
    List<Billboard> findAvailableBillboards(@Param("currentDate") Date currentDate);

}

