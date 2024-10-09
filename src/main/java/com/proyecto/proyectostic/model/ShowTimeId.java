package com.proyecto.proyectostic.model;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ShowTimeId implements Serializable {

    private Integer movie;  // Aquí se asocia la película por su ID
    private Date showtime_date;  // Fecha y hora de la función
    private Integer room;  // Sala en la que se proyecta la película

    // Constructor vacío
    public ShowTimeId() {}

    public ShowTimeId(Integer movie, Date showtime_date, Integer room) {
        this.movie = movie;
        this.showtime_date = showtime_date;
        this.room = room;
    }

    // Getters, Setters, equals, and hashCode

    public Integer getMovie() {
        return movie;
    }

    public void setMovie(Integer movie) {
        this.movie = movie;
    }

    public Date getShowtimeDate() {
        return showtime_date;
    }

    public void setShowtimeDate(Date showtimeDate) {
        this.showtime_date = showtimeDate;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowTimeId that = (ShowTimeId) o;
        return Objects.equals(movie, that.movie) && Objects.equals(showtime_date, that.showtime_date) && Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, showtime_date, room);
    }
}
