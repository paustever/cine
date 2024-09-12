package com.proyecto.proyectostic.model;

import java.io.Serializable;


public class BillboardId implements Serializable {

    private Integer movieId;
    private Integer cinemaId;

    public BillboardId() {
    }

    public BillboardId(Integer movieId, Integer cinemaId) {
        this.movieId = movieId;
        this.cinemaId = cinemaId;
    }
}
