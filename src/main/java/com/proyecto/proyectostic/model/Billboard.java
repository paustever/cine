package com.proyecto.proyectostic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
public class Billboard {

    @Id
    private Integer movieId;

    @Id
    private Integer cinemaId;

    public Integer getMovieId() {
        return movieId;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}