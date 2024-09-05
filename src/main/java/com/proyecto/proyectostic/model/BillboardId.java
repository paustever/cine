package com.proyecto.proyectostic.model;

import java.io.Serializable;

public class BillboardId implements Serializable {
    private Integer movieId;
    private Integer cinemaId;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }
}