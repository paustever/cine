package com.proyecto.proyectostic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "showtime")
public class ShowTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtime_id")
    private Integer showtimeId;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnore
    private Movie movie;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "showtime_date")
    private Date showtimeDate;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnore// Evitar la serialización del objeto Room en ShowTime
    private Room room;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL)
    @JsonManagedReference("showtime-reservations") // Serializar las reservas asociadas a ShowTime
    private List<Reservation> reservations;

    @ManyToOne
    @JoinColumn(name = "billboard_id", nullable = false)
    @JsonBackReference("billboard-showtimes")// Evitar la serialización del objeto Billboard en ShowTime
    private Billboard billboard;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "showtime_id")
    private List<Seat> reservedSeats;

    public List<Seat> getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(List<Seat> reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

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
