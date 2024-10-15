package com.proyecto.proyectostic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.proyectostic.model.*;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "showtime")
@IdClass(ShowTimeId.class)
public class ShowTime {

    @Id
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "showtime_date")
    private Date showtimeDate;

    @Id
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "billboard_id", nullable = false)
    private Billboard billboard;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Date getShowtimeDate() {
        return showtimeDate;
    }

    public void setShowtimeDate(Date showtimeDate) {
        this.showtimeDate = showtimeDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Billboard getBillboard() {
        return billboard;
    }

    public void setBillboard(Billboard billboard) {
        this.billboard = billboard;
    }
}
