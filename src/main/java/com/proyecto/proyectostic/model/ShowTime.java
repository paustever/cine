package com.proyecto.proyectostic.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer showtimeId;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Temporal(TemporalType.TIMESTAMP)
    private Date showtimeDate;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public ShowTime() {}

    // Getters y setters
    public Integer getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Integer showtimeId) {
        this.showtimeId = showtimeId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getShowtimeDate() {
        return showtimeDate;
    }

    public void setShowtimeDate(Date showtimeDate) {
        this.showtimeDate = showtimeDate;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}


