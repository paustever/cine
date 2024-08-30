package com.proyecto.proyectostic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

// Elimina esta línea
// import javax.persistence.*;

@Entity
@IdClass(BillboardId.class)
public class Billboard {

    @Id
    private Integer movieId;

    @Id
    private Integer cinemaId;

    // Getters y setters
}