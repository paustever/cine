package com.proyecto.proyectostic.model;

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
    private Movie movie;  // Clave compuesta - película

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    private Date showtime_date;  // Clave compuesta - fecha y hora de la función

    @Id
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;  // Clave compuesta - sala

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
    @ManyToOne
    @JoinColumn(name = "billboard_id", nullable = false)  // Nueva relación con Billboard
    private Billboard billboard;  // Clave compuesta - cartelera

    // Constructor vacío
    public ShowTime() {}

    // Getters y Setters

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Date getShowtime_date() {
        return showtime_date;
    }

    public void setShowtime_date(Date showtime_date) {
        this.showtime_date = showtime_date;
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
